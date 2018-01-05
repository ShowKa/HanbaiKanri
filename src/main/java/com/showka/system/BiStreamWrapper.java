package com.showka.system;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class BiStreamWrapper<O, N> {

	private final Collection<O> first;

	private final Collection<N> second;

	private Stream<O> firstStream;

	private Stream<N> secondStream;

	public static <O, N> BiStreamWrapper<O, N> of(Collection<O> first, Collection<N> second) {
		return new BiStreamWrapper<O, N>(first, second);
	}

	public Stream<O> first() {
		return firstStream;
	}

	public Stream<N> second() {
		return secondStream;
	}

	private BiStreamWrapper(Collection<O> first, Collection<N> second) {
		this.first = first;
		this.second = second;
		this.firstStream = first.stream();
		this.secondStream = second.stream();
	}

	private Stream<O> createFirstStream() {
		return first.stream();
	}

	private Stream<N> createSecondStream() {
		return second.stream();
	}

	public BiStreamWrapper<O, N> filterMatched(BiPredicate<O, N> predicate) {
		firstStream = firstStream.filter(f -> {
			return createSecondStream().anyMatch(s -> {
				return predicate.test(f, s);
			});
		});
		secondStream = secondStream.filter(s -> {
			return createFirstStream().anyMatch(f -> {
				return predicate.test(f, s);
			});
		});
		return this;
	}

	public BiStreamWrapper<O, N> filterNoneMatched(BiPredicate<O, N> predicate) {
		firstStream = firstStream.filter(f -> {
			return createSecondStream().noneMatch(s -> {
				return predicate.test(f, s);
			});
		});
		secondStream = secondStream.filter(s -> {
			return createFirstStream().noneMatch(f -> {
				return predicate.test(f, s);
			});
		});
		return this;
	}
}
