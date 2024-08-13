/**
 * [BoxLang]
 *
 * Copyright [2023] [Ortus Solutions, Corp]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ortus.boxlang.oshi.util;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ortus.boxlang.runtime.types.IStruct;
import ortus.boxlang.runtime.types.exceptions.BoxIOException;
import ortus.boxlang.runtime.types.exceptions.BoxRuntimeException;

public class IniFileTest {

	@DisplayName( "It can throw an exception on an invalid path" )
	@Test
	public void testInvalidPath() {
		// @formatter:off
		assertThrows( BoxIOException.class, () -> {
			new IniFile( Paths.get( "src/resources/invalid.ini" ) );
		} );
		// @formatter:on
	}

	@DisplayName( "It can load a valid file" )
	@Test
	public void testValidFile() {
		String validPath = "src/test/resources/test.ini";
		assertDoesNotThrow( () -> {
			new IniFile( Paths.get( validPath ) );
		} );

		// Given: General, Database, Logging and Features sections
		IniFile	target		= new IniFile( Paths.get( validPath ) );
		// When: The file is loaded
		// Then: The sections are loaded
		IStruct	sections	= target.getSections();
		assertThat( sections ).isNotEmpty();
		assertThat( sections.getKeysAsStrings() ).containsExactly( "General", "Database", "Logging", "Features" );
		assertThat( target.getSectionNames() )
		    .containsExactly( "General", "Database", "Logging", "Features" );
		// Test get entries with case-insensitive keys
		assertThat( target.getEntry( "Logging", "logLevel" ) ).isEqualTo( "DEBUG" );
		assertThat( target.getEntry( "logging", "MAXFILESIZE" ) ).isEqualTo( "10MB" );
		// Test invalid entry returns empty string
		assertThat( target.getEntry( "Logging", "invalid" ) ).isEmpty();
		// Invalid section throws exception
		assertThrows( BoxRuntimeException.class, () -> {
			target.getEntry( "invalid", "logLevel" );
		} );
	}

}
