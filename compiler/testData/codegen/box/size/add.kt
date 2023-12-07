// TARGET_BACKEND: WASM

// WASM_DCE_EXPECTED_OUTPUT_SIZE: wasm 12_559
// WASM_DCE_EXPECTED_OUTPUT_SIZE:  mjs  4_851

// FILE: test.kt

@JsExport
fun add(a: Int, b: Int) = a + b

// FILE: entry.mjs
import { add } from "./index.mjs"

const r = add(2, 3);
if (r != 5) throw Error("Wrong result: " + r);
