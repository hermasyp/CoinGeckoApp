package com.catnip.coingeckoapp.utils

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.test.assertEquals

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
suspend fun <T> Flow<T>.testWithScheduler(
    validate: suspend FlowTurbine<T>.() -> Unit
) {
    val testScheduler = coroutineContext[TestCoroutineScheduler]
    return if (testScheduler == null) {
        test(validate)
    } else {
        flowOn(UnconfinedTestDispatcher(testScheduler))
            .test(validate)
    }
}

/**
 * Creates a [FlowTestObserver] instance and starts collecting the values of the [Flow] immediately.
 * You must call [FlowTestObserver.finish] after all the values have been emitted to the flow in order to continue.
 */
fun <T> Flow<T>.test(scope: CoroutineScope): FlowTestObserver<T> {
    return FlowTestObserver(scope, this)
}

/**
 * A test class that collects the values of the provided `flow` in its own coroutine. The collected values are
 * accessible by [values]. It also provides certain "assert" functions, operating on the current list of collected
 * values.
 * At the end of the test, you should call [finish], especially, if some kind of "hot" Flow, like `SharedFlow` or
 * `StateFlow`, is tested.
 */
class FlowTestObserver<T>(scope: CoroutineScope, flow: Flow<T>) {

    private val valueCollector = mutableListOf<T>()

    private val job: Job = scope.launch {
        flow.collect { valueCollector.add(it) }
    }

    /**
     * The collected values from the Flow.
     */
    val values: List<T>
        get() = Collections.unmodifiableList(valueCollector)

    fun assertNoValues(message: String? = null): FlowTestObserver<T> {
        assertEquals(emptyList<T>(), this.valueCollector, message)
        return this
    }

    suspend fun assertValue(value: T, index: Int, message: String? = null): FlowTestObserver<T> {
        delay(1000)
        val actual = this.valueCollector[index]
        assertEquals(
            value,
            this.valueCollector[index],
            message ?: "Collected value at index $index does not match expected value.\n" +
            "Expected:\n$value \n" +
            "Actual:\n$actual\n"
        )
        return this
    }

    fun assertValues(vararg values: T, message: String? = null): FlowTestObserver<T> {
        assertEquals(values.toList(), this.valueCollector, message)
        return this
    }

    fun assertValueCount(count: Int, message: String? = null): FlowTestObserver<T> {
        assertEquals(
            count,
            this.valueCollector.size,
            message ?: "Expected $count values, but ${valueCollector.size} values have been emitted."
        )
        return this
    }

    /**
     *  Calls the provided [block] for each observed value with its corresponding index.
     *  There must be at least one value available, otherwise the test will fail.
     *  ```
     *  Example usage:
     *
     *  flow.test(scope).satisfies { index, value ->
     *      assertTrue(value.foo)
     *      assertEquals("bar", value.bar)
     *  }
     *
     *  ```
     *
     * @return The instance of `this` [FlowTestObserver].
     */
    fun satisfies(block: (index: Int, value: T) -> Unit): FlowTestObserver<T> {
        assertAtLeastOneValue()
        valueCollector.forEachIndexed { index, value ->
            if (value == null) throw AssertionError("value <null> was not expected")
            else block(index, value)
        }
        return this
    }

    /**
     *  Calls the provided [block] for each observed value. There must be at least one value
     *  available, otherwise the test will fail.
     *  ```
     *  Example usage:
     *
     *  flow.test(this).satisfies { value ->
     *      assertTrue(value.foo)
     *      assertEquals("bar", value.bar)
     *  }
     *
     *  ```
     *
     * @return The instance of `this` [FlowTestObserver].
     */
    suspend fun satisfies(block: (value: T) -> Unit): FlowTestObserver<T> {
        delay(1000)
        assertAtLeastOneValue()
        valueCollector.forEach {
            if (it == null) throw AssertionError("value <null> was not expected")
            else block(it)
        }
        return this
    }

    /**
     *  Calls the provided [block] for the value at the provided [index]. There must be at least one value
     *  available, otherwise the test will fail.
     *  ```
     *  Example usage:
     *
     *  flow.test(this).satisfies(index = 3) { valueAtIndex3 ->
     *      assertTrue(valueAtIndex3.foo)
     *      assertEquals("bar", valueAtIndex3.bar)
     *  }
     *
     *  ```
     *
     * @return The instance of `this` [FlowTestObserver].
     */
    fun satisfies(index: Int, block: (value: T) -> Unit): FlowTestObserver<T> {
        assertAtLeastOneValue()
        val valueToTest = valueCollector[index] ?: throw AssertionError("value <null> was not expected")
        block(valueToTest)
        return this
    }

    /**
     * Asserts, that the provided [value] was not emitted by the flow.
     */
    fun assertDoesNotContainValue(value: T, message: String? = null): FlowTestObserver<T> {
        assertEquals(this.valueCollector.filter { it == value }.size, 0, message)
        return this
    }

    /**
     * Calling this method will cancel the "collect" job. This continues the execution of the test method and enables
     * value assertions.
     */
    fun finish(): FlowTestObserver<T> {
        job.cancel()
        return this
    }

    private fun assertAtLeastOneValue(): FlowTestObserver<T> {
        if (valueCollector.isEmpty())
            throw AssertionError(
                "There was at least one expected value," +
                        " but no values have been emitted."
            )
        return this
    }
}