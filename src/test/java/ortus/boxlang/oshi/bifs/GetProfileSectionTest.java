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
import ortus.boxlang.runtime.types.IStruct;

public class GetProfileSectionTest {

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
		    result = getProfileSection( expandPath( "/src/test/resources/test.ini" ), "General" );
		    """,
		    context );
		// @formatter:on

		IStruct sResult = variables.getAsStruct( result );
		assertThat( sResult ).isNotEmpty();
		assertThat( sResult.containsKey( "appName" ) ).isTrue();
		assertThat( sResult.containsKey( "version" ) ).isTrue();
		assertThat( sResult.containsKey( "Author" ) ).isTrue();
	}
}
