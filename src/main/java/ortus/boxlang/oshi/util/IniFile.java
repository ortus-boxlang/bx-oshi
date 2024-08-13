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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import ortus.boxlang.runtime.dynamic.casters.StringCaster;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Array;
import ortus.boxlang.runtime.types.IStruct;
import ortus.boxlang.runtime.types.Struct;
import ortus.boxlang.runtime.types.exceptions.BoxIOException;
import ortus.boxlang.runtime.types.exceptions.BoxRuntimeException;

/**
 * This class represents an initialization file. An initialization file assigns values to configuration variables, also known as entries, that are
 * set when the system boots, the operating system comes up, or an application starts. An initialization file has the suffix ${code ini}; for example,
 * boot.ini, Win32.ini, devices.ini, or system.ini.
 *
 * e.g.:
 *
 * <pre>
 * [section1]
 * key1=value1
 * key2=value2
 *
 * [section2]
 * key3=value3
 * key4=value
 * </pre>
 */
public class IniFile {

	/**
	 * The filepath of the initialization file.
	 */
	private final Path	filepath;

	/**
	 * The sections of the initialization file.
	 */
	private IStruct		sections;

	/**
	 * Constructor with a Path object.
	 *
	 * @param filepath The filepath of the initialization file.
	 *
	 * @throws IllegalArgumentException If the file does not exist.
	 */
	public IniFile( Path filepath ) {
		this( filepath, false );
	}

	/**
	 * Constructor using a string filepath.
	 *
	 * @param filepath The absolute filepath of the initialization file.
	 */
	public IniFile( String filepath ) {
		this( Path.of( filepath ) );
	}

	/**
	 * Constructor with a Path object.
	 *
	 * @param filepath The filepath of the initialization file.
	 * @param force    If true, the file will be created if it doesn't exist.
	 *
	 * @throws BoxIOException If the file cannot be created.
	 */
	public IniFile( String filepath, Boolean force ) {
		this( Path.of( filepath ), force );
	}

	/**
	 * Constructor with a Path object.
	 *
	 * @param filepath The filepath of the initialization file.
	 * @param force    If true, the file will be created if it doesn't exist.
	 *
	 * @throws BoxIOException If the file cannot be created.
	 */
	public IniFile( Path filepath, Boolean force ) {
		this.filepath	= filepath.toAbsolutePath();
		this.sections	= new Struct( IStruct.TYPES.LINKED );
		if ( force ) {

			// Verify if the file exists, if not create it
			if ( !this.filepath.toFile().exists() ) {
				try {
					this.filepath.toFile().createNewFile();
				} catch ( IOException e ) {
					throw new BoxIOException( "The ini file [" + this.filepath.toString() + "] could not be created", e );
				}
			}

		}

		// Load it
		load();
	}

	/**
	 * Loads the file and returns the sections.
	 *
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public IStruct load() {
		// Read the file, parse the sections and store them in the sections variable.
		try ( BufferedReader reader = new BufferedReader( new FileReader( this.filepath.toFile() ) ) ) {
			String	line;
			IStruct	currentSection	= null;

			while ( ( line = reader.readLine() ) != null ) {
				line = line.trim();

				// Skip empty lines or comments
				if ( line.isEmpty() || line.startsWith( ";" ) || line.startsWith( "#" ) ) {
					continue;
				}

				// Check for section header and create section map
				if ( line.startsWith( "[" ) ) {
					String sectionName = line.substring( 1, line.length() - 1 ).trim();
					currentSection = new Struct( IStruct.TYPES.LINKED );
					this.sections.put( Key.of( sectionName ), currentSection );
				} else if ( currentSection != null && line.contains( "=" ) ) {
					// Process key-value pairs
					String[]	keyData		= line.split( "=", 2 );
					String		keyValue	= keyData.length > 1 ? keyData[ 1 ].trim() : "";
					currentSection.put( Key.of( keyData[ 0 ].trim() ), keyValue );
				}
			}
		} catch ( FileNotFoundException e ) {
			throw new BoxIOException( "The ini file [" + this.filepath.toString() + "] cannot be found", e );
		} catch ( IOException e ) {
			throw new BoxIOException( "Ini file could not be read", e );
		}

		return sections;
	}

	/**
	 * Gets the ini file's sections in Struct format.
	 *
	 * @return The sections of the ini file. Each section is a key and the value is a struct with the key-value pairs.
	 */
	public IStruct getSections() {
		return this.sections;
	}

	/**
	 * Verifies if a section exists in the ini file.
	 *
	 * @param section The section name.
	 *
	 * @return True if the section exists, false otherwise.
	 */
	public Boolean hasSection( String section ) {
		return this.sections.containsKey( Key.of( section ) );
	}

	/**
	 * Gets a specific section from the ini file.
	 *
	 * @param section The section name.
	 *
	 * @return The section as a struct or null if the section doesn't exist.
	 */
	public IStruct getSection( String section ) {
		return ( IStruct ) this.sections.get( Key.of( section ) );
	}

	/**
	 * Create a new section in the ini file.
	 * If the section already exists, it will return the existing section.
	 * If the section doesn't exist, it will create it and return it.
	 *
	 * @param section The section name.
	 *
	 * @return The section as a struct.
	 */
	public IStruct createSection( String section ) {
		return ( IStruct ) this.sections.computeIfAbsent( Key.of( section ), k -> new Struct( IStruct.TYPES.LINKED ) );
	}

	/**
	 * Removes a section from the ini file. If the section doesn't exist it will continue without removing the section.
	 *
	 * @param section The section name.
	 *
	 * @return The IniFile object now without the section.
	 */
	public IniFile removeSection( String section ) {
		this.sections.remove( Key.of( section ) );
		save();
		return this;
	}

	/**
	 * Gets the section names of the ini file as an array.
	 *
	 * @return An array of section names.
	 */
	public Array getSectionNames() {
		return new Array( this.sections.getKeysAsStrings() );
	}

	/**
	 * Get an entry value from a specific section. If the entry doesn't exist it will return an empty string.
	 * If the section doesn't exist it will return an exception.
	 *
	 * @param section The section name.
	 * @param entry   The entry name.
	 *
	 * @return The entry value or an empty string if the entry doesn't exist.
	 */
	public String getEntry( String section, String entry ) {
		IStruct thisSection = getSection( section );
		if ( thisSection == null ) {
			throw new BoxRuntimeException( "The section [" + section + "] does not exist in the ini file" );
		}
		return StringCaster.cast( thisSection.getOrDefault( Key.of( entry ), "" ) );
	}

	/**
	 * Set an entry value in a specific section.
	 * If the section doesn't exist it will create it.
	 *
	 * @param section The section name.
	 * @param entry   The entry name.
	 *
	 * @param value   The entire section as a struct with the new entry added.
	 */
	public IStruct setEntry( String section, String entry, String value ) {
		IStruct thisSection = createSection( section );
		thisSection.put( Key.of( entry ), value );
		save();
		return thisSection;
	}

	/**
	 * Removes an entry from a specific section. If the section doesn't exist it will continue without removing the entry.
	 * If the entry doesn't exist it will continue without removing the entry.
	 *
	 * @param section The section name.
	 * @param entry   The entry name.
	 *
	 * @return The IniFile object now without the entry.
	 */
	public IniFile removeEntry( String section, String entry ) {
		IStruct thisSection = getSection( section );
		if ( thisSection == null ) {
			return this;
		}

		thisSection.remove( Key.of( entry ) );
		save();

		return this;
	}

	/**
	 * Saves the ini file using the current sections and entries.
	 */
	public void save() {
		try ( BufferedWriter writer = new BufferedWriter( new FileWriter( this.filepath.toFile() ) ) ) {
			for ( Key sectionKey : this.sections.getKeys() ) {
				String sectionName = sectionKey.toString();
				writer.write( "[" + sectionName + "]" );
				writer.newLine();
				IStruct section = ( IStruct ) this.sections.get( sectionKey );
				for ( Key entryKey : section.getKeys() ) {
					String	entryName	= entryKey.toString();
					String	entryValue	= section.get( entryKey ).toString();
					writer.write( entryName + "=" + entryValue );
					writer.newLine();
				}
				writer.newLine();
			}
		} catch ( IOException e ) {
			throw new BoxIOException( "Ini file could not be saved", e );
		}

	}

}
