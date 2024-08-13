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
package ortus.boxlang.oshi.bifs;

import ortus.boxlang.oshi.util.IniFile;
import ortus.boxlang.oshi.util.KeyDictionary;
import ortus.boxlang.runtime.bifs.BIF;
import ortus.boxlang.runtime.bifs.BoxBIF;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.scopes.ArgumentsScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Argument;
import ortus.boxlang.runtime.types.IStruct;
import ortus.boxlang.runtime.types.Struct;

@BoxBIF
public class GetProfileSection extends BIF {

	/**
	 * Constructor
	 */
	public GetProfileSection() {
		super();
		declaredArguments = new Argument[] {
		    new Argument( true, Argument.STRING, Key.filepath ),
		    new Argument( true, Argument.STRING, KeyDictionary.section )
		};
	}

	/**
	 * Get's a section of an initialization file. An initialization file assigns values to configuration variables, also known as entries, that are
	 * name value pairs. An initialization file has the suffix INI; for example, boot.ini, Win32.ini.
	 *
	 * If the section does not exist, it will return an empty structure.
	 *
	 * @param context   The context in which the BIF is being invoked.
	 * @param arguments Argument scope for the BIF.
	 *
	 * @argument.filepath Absolute path (drive, directory, filename, extension) of initialization file; for example, C:\boot.ini
	 *
	 * @argument.section Section of initialization file to return
	 *
	 * @return A structure representing the section of the initialization file. If the section does not exist, it will return an empty structure.
	 */
	public IStruct _invoke( IBoxContext context, ArgumentsScope arguments ) {
		IStruct section = new IniFile( arguments.getAsString( Key.filepath ) ).getSection( arguments.getAsString( KeyDictionary.section ) );
		return ( section == null ) ? new Struct() : section;
	}

}
