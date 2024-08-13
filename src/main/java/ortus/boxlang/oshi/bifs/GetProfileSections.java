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
import ortus.boxlang.runtime.bifs.BIF;
import ortus.boxlang.runtime.bifs.BoxBIF;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.scopes.ArgumentsScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Argument;
import ortus.boxlang.runtime.types.IStruct;

@BoxBIF
public class GetProfileSections extends BIF {

	/**
	 * Constructor
	 */
	public GetProfileSections() {
		super();
		declaredArguments = new Argument[] {
		    new Argument( true, Argument.STRING, Key.filepath )
		};
	}

	/**
	 * Gets all the sections of an initialization file. An initialization file assigns values to configuration variables, also known as entries, that are
	 * set when the system boots, the operating system comes up, or an application starts. An initialization file has the suffix INI; for example,
	 * boot.ini, Win32.ini.
	 *
	 * @param context   The context in which the BIF is being invoked.
	 * @param arguments Argument scope for the BIF.
	 *
	 * @argument.filepath Absolute path (drive, directory, filename, extension) of initialization file; for example, C:\boot.ini
	 *
	 * @return An initialization file, as a structure whose format is as follows:
	 *         - Each initialization file section name is a key in the structure
	 *         - Each list of entries in a section of an initialization file is a value in the structure
	 *         If there is no value, returns an empty string.
	 */
	public IStruct _invoke( IBoxContext context, ArgumentsScope arguments ) {
		return new IniFile( arguments.getAsString( Key.filepath ) ).getSections();
	}

}
