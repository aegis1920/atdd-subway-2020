package wooteco.subway.members.member.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Person {
    CHILD(age -> age >= 6 && age < 13, 350, 0.5),
    YOUTH(age -> age >= 13 && age < 19, 350, 0.8),
    NONE(age -> true, 0, 1);

    private final Predicate<Integer> age;
    private final int deduction;
    private final double discount;

    Person(Predicate<Integer> age, int deduction, double discount) {
        this.age = age;
        this.deduction = deduction;
        this.discount = discount;
    }

    public static long calculate(long fare, int age) {
        Person person = findPerson(age);
        fare -= person.deduction;
        return (long) (fare * person.discount);
    }

    private static Person findPerson(int age) {
        return Arrays.stream(Person.values())
            .filter(person -> person.age.test(age))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 나이에 맞는 정책이 없습니다!"));
    }
}
