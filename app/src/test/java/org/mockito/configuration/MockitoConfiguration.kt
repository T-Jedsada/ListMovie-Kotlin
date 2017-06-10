package org.mockito.configuration

import io.reactivex.Observable
import io.reactivex.Single
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

class MockitoConfiguration : DefaultMockitoConfiguration() {
	override fun getDefaultAnswer(): Answer<Any> {
		return object : ReturnsEmptyValues() {
			override fun answer(inv: InvocationOnMock): Any {
				val type = inv.method.returnType
				if (type.isAssignableFrom(Observable::class.java)) {
					return Observable.error<Unit>(createException(inv, "Observable"))
				}
				else if (type.isAssignableFrom(Single::class.java)) {
					return Single.error<Unit>(createException(inv, "Single"))
				}
				else if (type.isAssignableFrom(Observable::class.java)) {
					return Observable.error<Unit>(createException(inv, "Observable"))
				}
				else if (type.isAssignableFrom(Single::class.java)) {
					return Single.error<Unit>(createException(inv, "Single"))
				}
				else {
					return super.answer(inv)
				}
			}
		}
	}

	private fun createException(invocation: InvocationOnMock, className: String): RuntimeException {
		val s = invocation.toString()
		return RuntimeException("No mock defined for invocation $s\nwhen(${s.substring(0, s.length - 1)}).thenReturn($className.just());")
	}
}
