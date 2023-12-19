@kotlin.js.JsName(name = "Array")
public open external class JsArray<E> : kotlin.js.collections.JsReadonlyArray<E> {
    public constructor JsArray<E>()
}

@kotlin.js.JsName(name = "Map")
public open external class JsMap<K, V> : kotlin.js.collections.JsReadonlyMap<K, V> {
    public constructor JsMap<K, V>()
}

@kotlin.js.JsName(name = "ReadonlyArray")
public external interface JsReadonlyArray<out E> {
}

@kotlin.js.JsName(name = "ReadonlyMap")
public external interface JsReadonlyMap<K, out V> {
}

@kotlin.js.JsName(name = "ReadonlySet")
public external interface JsReadonlySet<out E> {
}

@kotlin.js.JsName(name = "Set")
public open external class JsSet<E> : kotlin.js.collections.JsReadonlySet<E> {
    public constructor JsSet<E>()
}
