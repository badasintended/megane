package badasintended.megane.api.function;

public class Functions {

    @FunctionalInterface
    public interface Obj2Bool<T> {

        boolean apply(T t);

    }

    @FunctionalInterface
    public interface Obj2Double<T> {

        double apply(T t);

    }

    @FunctionalInterface
    public interface Obj2Int<T> {

        int apply(T t);

    }

    @FunctionalInterface
    public interface ObjObj2Int<A, B> {

        int apply(A a, B b);

    }

    @FunctionalInterface
    public interface ObjInt2Obj<A, R> {

        R apply(A a, int i);

    }

    @FunctionalInterface
    public interface ObjInt2Double<A> {

        double apply(A a, int i);

    }

    @FunctionalInterface
    public interface TriConsumer<A, B, C> {

        void apply(A a, B b, C c);

    }

}
