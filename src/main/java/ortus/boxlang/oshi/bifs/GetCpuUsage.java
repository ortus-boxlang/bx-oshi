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

import ortus.boxlang.runtime.bifs.BIF;
import ortus.boxlang.runtime.bifs.BoxBIF;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.dynamic.casters.LongCaster;
import ortus.boxlang.runtime.scopes.ArgumentsScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Argument;
import oshi.SystemInfo;

@BoxBIF
public class GetCpuUsage extends BIF {

	/**
	 * Constructor
	 */
	public GetCpuUsage() {
		super();
		declaredArguments = new Argument[] {
		    new Argument( false, Argument.NUMERIC, Key.interval, 1000 )
		};
	}

	/**
	 * Get's the CPU usage from the OSHI library. You can use this to get the CPU usage
	 * of the system.
	 *
	 * @param context   The context in which the BIF is being invoked.
	 * @param arguments Argument scope for the BIF.
	 *
	 * @argument.interval The interval in milliseconds to get the CPU usage. The default is 1000.
	 *
	 * @return The CPU usage of the system.
	 */
	public Double _invoke( IBoxContext context, ArgumentsScope arguments ) {
		Long delay = LongCaster.cast( arguments.get( Key.interval ) );
		return new SystemInfo().getHardware().getProcessor().getSystemCpuLoad( delay );
	}

}
