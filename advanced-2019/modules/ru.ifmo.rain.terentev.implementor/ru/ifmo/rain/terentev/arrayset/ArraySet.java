package ru.ifmo.rain.terentev.arrayset;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements NavigableSet<T> {
    private final Comparator<? super T> comparator;
    private final List<T> data;

    public ArraySet() {
        data = Collections.emptyList();
        comparator = null;
    }

    public ArraySet(Collection<? extends T> data) {
        this(data, null);
    }

    public ArraySet(Collection<? extends T> collection, Comparator<? super T> comparator) {
        this.comparator = comparator;
        TreeSet<T> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(collection);
        data = new ArrayList<>(treeSet);
    }

    private ArraySet(List<T> prevData, Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.data = prevData;

    }

    @Override
    public T lower(T t) {
        return getElementOrNull(getLowerPosition(t));
    }

    private int getLowerPosition(T t) {
        return getPosition(t, -1, -1);
    }

    private int getFloorPosition(T t) {
        return getPosition(t, 0, -1);
    }

    private int getCeilingPosition(T t) {
        return getPosition(t, 0, 0);
    }

    private int getHigherPosition(T t) {
        return getPosition(t, 1, 0);
    }

    @Override
    public T floor(T t) {
        return getElementOrNull(getFloorPosition(t));
    }

    @Override
    public T ceiling(T t) {
        return getElementOrNull(getCeilingPosition(t));
    }

    @Override
    public T higher(T t) {
        return getElementOrNull(getHigherPosition(t));
    }

    @Override
    public T pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(data).iterator();
    }

    @Override
    public NavigableSet<T> descendingSet() {
        ReversedList<T> result = new ReversedList<>(data);
        result.reverse();
        return new ArraySet<>(result, Collections.reverseOrder(comparator));
    }

    @Override
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    private int findIndexFromHead(T element, boolean inclusive) {
        int index = Collections.binarySearch(data, element, comparator);
        return index < 0 ? ~index : (inclusive ? index : index + 1);
    }

    private int findIndexFromTail(T element, boolean inclusive) {
        int index = Collections.binarySearch(data, element, comparator);
        return index < 0 ? ~index - 1 : (inclusive ? index : index - 1);
    }

    private int getPosition(T pattern, int includeType, int strictType) {
        int res = Collections.binarySearch(data, Objects.requireNonNull(pattern), comparator);
        return res < 0 ? ~res + strictType : res + includeType;
    }

    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        return comparator == null ? ((Comparable) a).compareTo(b) : comparator.compare(a, b);
    }

    @Override
    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException();
        }
        int fromI = fromInclusive ? getPosition(fromElement, 0, 0) : getPosition(fromElement, 1, 0);
        int toI = (toInclusive ? getFloorPosition(toElement) : getLowerPosition(toElement)) + 1;
        if (toI + 1 == fromI) {
            toI = fromI;
        }
        return new ArraySet<>(data.subList(fromI, toI), comparator);
    }

    @Override
    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        int index = findIndexFromTail(toElement, inclusive) + 1;
        return new ArraySet<>(data.subList(0, index), comparator);
    }

    @Override
    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        int index = findIndexFromHead(fromElement, inclusive);
        return new ArraySet<>(data.subList(index, data.size()), comparator);
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public T first() {
        emptyDataCheck();
        return data.get(0);
    }

    @Override
    public T last() {
        emptyDataCheck();
        return data.get(data.size() - 1);
    }

    @Override
    public int size() {
        return data.size();
    }

    private T getElementOrNull(int index) {
        return (index < 0 || index >= data.size()) ? null : data.get(index);
    }

    private void emptyDataCheck() {
        if (data.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return Collections.binarySearch(data, (T) o, comparator) >= 0;
    }
}

