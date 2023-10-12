// LANGUAGE: -ProhibitDifferentMemberScopesInOpenExpect
// MODULE: m1-common
// FILE: common.kt

open class Base {
    <!INCOMPATIBLE_EXPECT_ACTUAL{JVM}!>open fun foo(param: Int) {}<!>
}

<!INCOMPATIBLE_EXPECT_ACTUAL{JVM}!>expect open class Foo1 : Base<!>
expect open class Foo2 : Base
expect open class Foo3 {
    open fun foo(param: Int)
}

// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt

actual open <!ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING!>class Foo1<!> : Base() {
    override fun <!PARAMETER_NAME_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING!>foo<!>(paramNameChanged: Int) {}
}

actual typealias Foo2 = Foo2Java
actual typealias Foo3 = Foo3Java

// FILE: Foo2Java.java

public class Foo2Java extends Base {
    @Override
    public void foo(int paramNameChanged) {}
}

// FILE: Foo3Java.java
public class Foo3Java {
    public void foo(int paramNameChanged) {}
}
