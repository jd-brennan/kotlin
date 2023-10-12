// LANGUAGE: -ProhibitDifferentMemberScopesInOpenExpect
// MODULE: m1-common
// FILE: common.kt
interface Base {
    <!INCOMPATIBLE_EXPECT_ACTUAL{JVM}!>fun foo() {}<!>
}
<!INCOMPATIBLE_EXPECT_ACTUAL{JVM}!>expect abstract class Foo() : Base<!>


// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt

actual abstract <!ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING!>class Foo<!> : Base {
    <!MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING!>abstract<!> override fun foo()
}
