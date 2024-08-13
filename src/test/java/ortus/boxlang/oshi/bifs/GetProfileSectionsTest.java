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

public class GetProfileSectionsTest {

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
		    result = getProfileSections( expandPath( "/src/test/resources/test.ini" ) );
		    """,
		    context );
		// @formatter:on

		IStruct sResult = variables.getAsStruct( result );
		assertThat( sResult ).isNotEmpty();
		assertThat( sResult.getKeysAsStrings() ).containsExactly( "General", "Database", "Logging", "Features" );
		assertThat( sResult.containsKey( "General" ) ).isTrue();
		assertThat( sResult.containsKey( "Database" ) ).isTrue();
		assertThat( sResult.containsKey( "Logging" ) ).isTrue();
		assertThat( sResult.containsKey( "Features" ) ).isTrue();
	}
}
