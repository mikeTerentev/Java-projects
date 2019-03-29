package ru.ifmo.rain.terentev.implementor;

import info.kgeorgiy.java.advanced.implementor.*;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * Class gives ability to user to
 * <ul>
 * <li>create .java files implementations  of  classes and interfaces</li>
 * <li>compile implementations and pack compiled .class into Jar-archives </li>
 * </ul>
 *
 * @author Mike Terentev
 * @see Implementor#implement(Class, Path)
 * @see Implementor#implementJar(Class, Path)
 */
public class Implementor implements JarImpler {
    /**
     *  {@link String} of space
     */
    private static final String SPACE = " ";
    /**
     *  {@link String} of left brace
     */
    private static final String LBRACE = "{";
    /**
     *  {@link String} of end of file
     */
    private static final String EOL = System.lineSeparator();
    /**
     *  {@link String} of end of right brace
     */
    private static final String RBRACE = "}";
    /**
     *  {@link String} of end of semicolon
     */
    private static final String SEMICOLON = ";";
    /**
     *  {@link String} of end of comma
     */
    private static final String COMMA = ",";
    /**
     *  {@link String} of tab
     */
    private static final String SHIFT = "    ";

    /**
     * Creates new instance of {@link Implementor}
     */
    public Implementor() {}
    /**
     * Gets class or interface to implement and generates source code for class, implementing it. <br>
     * Output file is created in subdirectory of the given path.
     * The resulting class is located in a package, whose name coincides with the name of the package,
     * in which the given-user class is located.
     *
     * @throws ImplerException if the given class cannot be generated as:
     *                         <ul>
     *                         <li> Some arguments are null</li>
     *                         <li> Given class is primitive or final or array or {@link Enum}. </li>
     *                         <li> Given class is not an interface and contains only private constructors. </li>
     *                         <li> The problems with I/O occurred during implementation. </li>
     *                         </ul>
     */
    @Override
    public void implement(Class<?> aClass, Path path) throws ImplerException {
        if (aClass == null || path == null) {
            throw new ImplerException("InvalidArgs");
        }
        if (aClass.isArray() || aClass == Enum.class || Modifier.isFinal(aClass.getModifiers()) || aClass.isPrimitive()) { //todo sth else??
            throw new ImplerException("Illegal input : " + aClass.getSimpleName());
        }
        path = getFullPath(aClass, path, ".java");
        mkdirToPath(path);
        try (Writer writer = Files.newBufferedWriter(path)) {
            writer.write(toUnicode(getPackagesPart(aClass).concat(EOL)));
            writer.write(toUnicode(getHeader(aClass)));
            if (!aClass.isInterface()) {
                writer.write(toUnicode(getConstructors(aClass)));
            }
            writer.write(toUnicode(getAbstractMethods(aClass)));
            writer.write(toUnicode(RBRACE));
        } catch (IOException e) {
            throw new ImplerException("Writer error", e);
        }
    }

    /**
     * Represents {@link String},which contains implementation of abstract methods of given {@link Class}
     *
     * @param aClass given {@link Class} to implement abstract methods
     * @return {@link String} which represents abstract methods of given {@link Class}
     */
    private String getAbstractMethods(Class<?> aClass) {
        StringBuilder res = new StringBuilder();
        Set<Method> methods = new HashSet<>();
        getAbstractMethods(aClass.getMethods(), methods);
        getAbstractMethods(aClass.getDeclaredMethods(), methods);
        for (Method method : methods) {
            res.append(getExecutable(method));
        }
        return res.toString();
    }

    /**
     * Takes array of {@link Method} and add only declared as abstract of them to {@link Set},
     * which is second argument
     *
     * @param methods given array of {@link Method}
     * @param storage {@link Set} storing methods
     */
    private static void getAbstractMethods(Method[] methods, Set<Method> storage) {
        Arrays.stream(methods)
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .collect(Collectors.toCollection(() -> storage));
    }

    /**
     * Represents {@link String},which contains implementation of constructors of given {@link Class}
     *
     * @param aClass given class to implement constructors
     * @return {@link String}, which represents constructors of given {@link Class}
     * @throws ImplerException if class doesn't have any non-private constructors
     */
    private String getConstructors(Class<?> aClass) throws ImplerException {
        StringBuilder res = new StringBuilder();
        Constructor<?>[] constructors = Arrays.stream(aClass.getDeclaredConstructors())
                .filter(constructor -> !Modifier.isPrivate(constructor.getModifiers()))
                .toArray(Constructor<?>[]::new);
        if (constructors.length > 0) {
            for (Constructor<?> constructor : constructors) {
                res.append(getExecutable(constructor));
            }
            return res.toString();
        } else {
            res.append("public ").append(aClass.getSimpleName())
                    .append(LBRACE)
                    .append(EOL)
                    .append(RBRACE);
        }
        throw new ImplerException("Bad Class : no constructors");
    }

    /**
     * Returns {@link String}, which represents fully constructed {@link Executable}, which
     * includes returning  default value of return type of such {@link Method}
     * or calling constructor of super class if argument is {@link Constructor}
     *
     * @param exec {@link Method}  or {@link Constructor} to process
     * @return {@link String} representing code of such {@link Executable}
     */
    private String getExecutable(Executable exec) {
        StringBuilder builder = new StringBuilder();
        int modifiers = exec.getModifiers() & ~Modifier.NATIVE & ~Modifier.ABSTRACT & ~Modifier.TRANSIENT;
        return builder.append(SHIFT)
                .append(Modifier.toString(modifiers))
                .append(modifiers > 0 ? SPACE : "")
                .append(getExecutableHeader(exec))
                .append(getParameters(exec, true))
                .append(getExceptions(exec))
                .append(SPACE)
                .append(LBRACE)
                .append(EOL)
                .append(getShift(2))
                .append(getBody(exec))
                .append(SEMICOLON)
                .append(EOL)
                .append(getShift(1))
                .append(RBRACE)
                .append(EOL)
                .append(EOL)
                .toString();
    }


    /**
     * Returns list of parameters of {@link Executable}, surrounded by round parenthesis,
     * optionally with their types
     *
     * @param exec           {@link Executable}
     * @param isTypeNameIncl adding parameter type flag
     * @return {@link String} representing parameters of  {@link Executable}
     */
    private static String getParameters(Executable exec, boolean isTypeNameIncl) {
        return Arrays.stream(exec.getParameters())
                .map(param -> getParam(param, isTypeNameIncl))
                .collect(Collectors.joining(COMMA + SPACE, "(", ")"));
    }

    /**
     * Returns  {@link String} representing name of {@link Parameter}, optionally with  type
     *
     * @param param          {@link Parameter}
     * @param isTypeNameIncl adding parameter type flag
     * @return {@link String} representing parameter of {@link Parameter}
     */
    private static String getParam(Parameter param, boolean isTypeNameIncl) {
        return (isTypeNameIncl ? param.getType().getCanonicalName() + SPACE : "") + param.getName();
    }

    /**
     * Returns list of exceptions, that given {@link Executable} can throw
     *
     * @param exec {@link Executable} to get exceptions
     * @return {@link String} which contains list of exceptions
     */
    private static String getExceptions(Executable exec) {
        StringBuilder res = new StringBuilder();
        Class<?>[] exceptions = exec.getExceptionTypes();
        if (exceptions.length > 0) {
            res.append(SPACE + "throws" + SPACE);
        }
        return res.append(
                Arrays.stream(exceptions)
                        .map(Class::getCanonicalName)
                        .collect(Collectors.joining(COMMA + SPACE)))
                .toString();
    }

    /**
     * Gets default value of class
     *
     * @param token class to get default value
     * @return {@link String} representing value
     */
    private static String getDefReturnValue(Class<?> token) {
        if (token.equals(boolean.class)) {
            return " false";
        } else if (token.equals(void.class)) {
            return "";
        } else if (token.isPrimitive()) {
            return " 0";
        }
        return " null";
    }

    /**
     * If given {@link Executable} is instance of {@link Constructor}, calls constructor of super class,
     * or return default value of return type of {@link Method}
     *
     * @param exec given {@link Constructor} or {@link Method}
     * @return {@link String} representing body of {@link Executable} whiсh is above
     */
    private static String getBody(Executable exec) {
        if (exec instanceof Method) {
            return "return".concat(getDefReturnValue(((Method) exec).getReturnType()));
        } else {
            return "super".concat(getParameters(exec, false));
        }
    }

    /**
     * Returns {@link String} consisting of several tabs,the number of which is given by cnt
     *
     * @param cnt number of tabs to return
     * @return {@link String} consisting of given amount of tabs
     */
    private static String getShift(int cnt) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            res.append(SHIFT);
        }
        return res.toString();
    }

    /**
     * Build {@link String}, which represents return type and name of given {@link Executable}
     *
     * @param exec {@link Executable}
     * @return {@link String} consisting  return type and name of {@link Executable}
     */
    private String getExecutableHeader(Executable exec) {
        return exec instanceof Method ? ((Method) exec).getReturnType()
                .getCanonicalName()
                .concat(SPACE)
                .concat(exec.getName()) : getImplClassName(((Constructor<?>) exec).getDeclaringClass());
    }

    /**
     * Returns first line of declaration of the class, containing name,
     * base class or implemented interface
     *
     * @param aClass base class or implemented interface
     * @return {@link String} representing beginning of class declaration
     */
    private String getHeader(Class<?> aClass) {
        return ("public ")
                .concat("class")
                .concat(SPACE)
                .concat(getImplClassName(aClass))
                .concat(SPACE)
                .concat(aClass.isInterface() ? "implements" : "extends")
                .concat(SPACE)
                .concat(aClass.getSimpleName())
                .concat(SPACE)
                .concat(LBRACE)
                .concat(EOL);
    }

    /**
     * Gets package of given file.  If package don't exist, class is situated in default package
     *
     * @param aClass class to get package
     * @return {@link String} representing package of given class
     */
    private String getPackagesPart(Class<?> aClass) {
        if (aClass.getPackage().getName().equals("")) {
            return EOL;
        } else {
            return "package "
                    .concat(aClass.getPackage().getName())
                    .concat(SEMICOLON)
                    .concat(EOL);
        }
    }

    /**
     * Creates parent directories of file
     *
     * @param path file to create parent directories
     * @throws ImplerException if error occurred during creation
     */
    private void mkdirToPath(Path path) throws ImplerException {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException ignored) {
            throw new ImplerException("Can't create directories");
        }
    }

    /**
     * Return path to file, containing implementation of given class,
     * located in directory represented by path
     *
     * @param aClass    class to get name from
     * @param path      {@link Path} path to parent directory of class
     * @param extension {@link String} file extension
     * @return {@link Path} representing path to file
     */
    private Path getFullPath(Class<?> aClass, Path path, String extension) {
        return path
                .resolve(aClass.getPackage()
                        .getName()
                        .replace(".", "/"))
                .resolve(getImplClassName(aClass)
                        .concat(extension));
    }

    /**
     * Adds "Impl" to simple name of given class
     *
     * @param token class to add suffix name
     * @return {@link String} with specified class name
     */
    private static String getImplClassName(Class<?> token) {
        return token.getSimpleName().concat("Impl");
    }

    /**
     * Checks if any of given arguments is null
     *
     * @param args list of arguments
     * @throws ImplerException if any arguments is null
     */
    private static void nullAssertion(Object... args) throws ImplerException {
        for (Object o : args) {
            if (o == null) {
                throw new ImplerException("Null arguments appeared");
            }
        }
    }

    /**
     * Produces .jar file implementing class or interface.
     * Method gets class or interface to implement and generates .jar-archive, containing class,
     * implementing it.
     * .jar-file contains compiled public class, implementing given class or interface,which was suffix Impl in classnam.
     *
     * @param aClass      class or interface to implement
     * @param jarFilePath specified location of generated .jar
     */
    @Override
    public void implementJar(Class<?> aClass, Path jarFilePath) throws ImplerException {
        nullAssertion(aClass, jarFilePath);
        mkdirToPath(jarFilePath);
        Path tmpdir;
        try {
            tmpdir = Files.createTempDirectory(jarFilePath.toAbsolutePath().getParent(), "tmp");
        } catch (IOException e) {
            throw new ImplerException("Failed to make a temporary directory", e);
        }

        try {
            implement(aClass, tmpdir);
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            String[] args = new String[]{
                    "-cp",
                    "-encoding",
                    "utf8",
                    getClassPath(aClass) + File.pathSeparator + tmpdir.toString(),
                    tmpdir.resolve(getFullPath(aClass, tmpdir, ".java")).toString()
            };

            if (javaCompiler == null || javaCompiler.run(null, null, null, args) != 0) {
                throw new ImplerException("Failed to compile generated java classes");
            }

            Manifest manifest = new Manifest();
            Attributes attributes = manifest.getMainAttributes();
            attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");

            try (JarOutputStream jarWriter = new JarOutputStream(Files.newOutputStream(jarFilePath), manifest)) {
                jarWriter.putNextEntry(
                        new ZipEntry(aClass
                                .getCanonicalName()
                                .replace('.', '/')
                                .concat("Impl")
                                .concat(".class")));
                Files.copy(tmpdir.resolve(getFullPath(aClass, tmpdir, ".class")), jarWriter);
            } catch (IOException e) {
                throw new ImplerException("Writing a jar file an error occurred", e);
            }
        } finally {
            try {
                Files.walk(tmpdir)
                        .map(Path::toFile)
                        .sorted(Comparator.reverseOrder())
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new ImplerException("Failed deleting temporary files in " + tmpdir.toString());
            }
        }

    }

    /**
     * This method starts implementation, depending on amount of arguments from command line.
     * There are two legal variants of arguments:
     * <ul>
     * <li> className rootPath - runs {@link #implement(Class, Path)} with second and third arguments</li>
     * <li>  -jar className jarPath - runs {@link #implementJar(Class, Path)} with second and third arguments</li>
     * </ul>
     *
     * @param args arguments for running program
     */
    public static void main(String[] args) {
        if (args == null || (args.length != 2 && args.length != 3)) {
            System.out.println("Wrong arguments format");
            return;
        }

        for (String arg : args) {
            if (arg == null) {
                System.out.println("Null argument appeared");
                return;
            }
        }
        Implementor jarImplementor = new Implementor();

        try {
            if (args.length == 2) {
                try {
                    jarImplementor.implement(Class.forName(args[0]), Paths.get(args[1]));
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found :" + args[0]);
                }
            } else if (args.length == 3 && "-jar".equals(args[0])) {
                try {
                    jarImplementor.implementJar(Class.forName(args[1]), Paths.get(args[2]));
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found :" + args[1]);
                }
            } else {
                System.out.println("Wrong arguments format");
            }
        } catch (InvalidPathException e) {
            System.out.println("Invalid path given");
        } catch (ImplerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method finds classpath of given class
     * @param  aClass {@link Class} class classpath to take
     * @return {@link String} classpath
     */
    private static String getClassPath(Class<?> aClass) {
        try {
            return Path.of(aClass.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Converts given string to unicode escaping
     * @param line {@link String} to convert
     * @return converted string
     */
    private static String toUnicode(String line) {
        StringBuilder b = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c >= 128) {
                b.append(String.format("\\u%04X", (int) c));
            } else {
                b.append(c);
            }
        }
        return b.toString();
    }

}
