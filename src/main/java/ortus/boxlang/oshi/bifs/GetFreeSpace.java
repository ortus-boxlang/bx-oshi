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

import java.util.Set;

import ortus.boxlang.runtime.bifs.BIF;
import ortus.boxlang.runtime.bifs.BoxBIF;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.scopes.ArgumentsScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Argument;
import ortus.boxlang.runtime.types.exceptions.BoxRuntimeException;
import ortus.boxlang.runtime.validation.Validator;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.software.os.OSFileStore;

@BoxBIF
public class GetFreeSpace extends BIF {

	/**
	 * Constructor
	 */
	public GetFreeSpace() {
		super();
		declaredArguments = new Argument[] {
		    new Argument( true, Argument.STRING, Key.path, Set.of( Validator.NON_EMPTY ) )
		};
	}

	/**
	 * Get the free space of the volume at the given path.
	 * The volume can be an attached volume or physical disk
	 *
	 * @param context   The context in which the BIF is being invoked.
	 * @param arguments Argument scope for the BIF.
	 *
	 * @argument.path The path to the volume or disk name.
	 *
	 * @return The free space of the path.
	 */
	public Object _invoke( IBoxContext context, ArgumentsScope arguments ) {
		SystemInfo	si			= new SystemInfo();
		String		volumeName	= arguments.getAsString( Key.path );

		// Check for mounted file systems
		for ( OSFileStore fs : si.getOperatingSystem().getFileSystem().getFileStores() ) {
			if ( fs.getMount().equals( volumeName ) ) {
				return fs.getTotalSpace();
			}
		}

		// Check for physical disks if volume name not found
		// Useful, so you can get things like `disk1` instead of `/dev/disk1`
		for ( HWDiskStore disk : si.getHardware().getDiskStores() ) {
			if ( disk.getName().equals( volumeName ) ) {
				return disk.getSize() - disk.getWriteBytes();
			}
		}

		// Volume not found
		throw new BoxRuntimeException( "Volume not found: " + volumeName );
	}

}
