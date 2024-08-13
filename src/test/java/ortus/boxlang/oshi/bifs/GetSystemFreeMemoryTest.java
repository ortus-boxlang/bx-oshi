package ortus.boxlang.oshi.bifs;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ortus.boxlang.runtime.BoxRuntime;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.context.ScriptingRequestBoxContext;
import ortus.boxlang.runtime.scopes.IScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.scopes.VariablesScope;

public class GetSystemFreeMemoryTest {

	static BoxRuntime	instance;
	IBoxContext			context;
	IScope				variables;
	static Key			result	= new Key( "result" );

	@BeforeAll
	public static void setUp() {
		instance = BoxRuntime.getInstance( true );
	}

	@BeforeEach
	public void setupEach() {
		context		= new ScriptingRequestBoxContext( instance.getRuntimeContext() );
		variables	= context.getScopeNearby( VariablesScope.name );
	}

	@DisplayName( "It can test the bif" )
	@Test
	public void testBif() {
		// @formatter:off
		instance.executeSource(
		    """
		    result = GetSystemFreeMemory();
		    """,
		    context );
		// @formatter:on
		System.out.println( variables.get( result ) );
		assertThat( variables.get( result ) ).isNotNull();
	}
}
