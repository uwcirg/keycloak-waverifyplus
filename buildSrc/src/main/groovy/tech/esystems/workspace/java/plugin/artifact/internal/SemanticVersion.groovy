/**
 * Represents a semantic version, consisting of major, minor, patch, and optional GA and snapshot components.
 * Allows building version strings and validating version values within specified ranges.
 */
package tech.esystems.workspace.java.plugin.artifact.internal

/**
 * The SemanticVersion class holds versioning information including major, minor, patch numbers,
 * an optional GA version, and a snapshot flag. It supports building version strings and provides
 * validation to ensure components are within valid ranges.
 */
class SemanticVersion {

	/** Major version, expected to be between 0 and 99. */

	Integer major

	/** Minor version, expected to be between 0 and 99. */

	Integer minor

	/** Patch version, expected to be between 0 and 99. */

	Integer patch

	/** Optional GA (General Availability) version, expected to be between 0 and 999. */

	Integer ga

	/** Flag indicating if this version is a snapshot. */

	Boolean snap

	/**
	 * Default constructor initializing version to 0.0.0, not a snapshot.
	 */
	SemanticVersion () {
		this.major = 0
		this.minor = 0
		this.patch = 0
		this.snap = false
	}

	/**
	 * Constructor initializing the version with major, minor, patch, and snapshot flag.
	 * @param major Major version, must be between 0 and 99.
	 * @param minor Minor version, must be between 0 and 99.
	 * @param patch Patch version, must be between 0 and 99.
	 * @param snap Boolean flag indicating if this is a snapshot version.
	 * @throws IllegalArgumentException If any version component is out of range.
	 */
	SemanticVersion (Integer major, Integer minor, Integer patch, Boolean snap) {
		validateVersionComponents(major, minor, patch, null)
		this.major = major
		this.minor = minor
		this.patch = patch
		this.snap = snap
	}

	/**
	 * Constructor initializing the version with major, minor, patch, GA, and snapshot flag.
	 * @param major Major version, must be between 0 and 99.
	 * @param minor Minor version, must be between 0 and 99.
	 * @param patch Patch version, must be between 0 and 99.
	 * @param ga GA version, must be between 0 and 999.
	 * @param snap Boolean flag indicating if this is a snapshot version.
	 * @throws IllegalArgumentException If any version component is out of range.
	 */
	SemanticVersion (Integer major, Integer minor, Integer patch, Integer ga, Boolean snap) {
		validateVersionComponents(major, minor, patch, ga)
		this.major = major
		this.minor = minor
		this.patch = patch
		this.ga = ga
		this.snap = snap
	}

	/**
	 * Returns the base version in the format 'major.minor.patch'.
	 * @return String representation of the base version.
	 */
	String getBaseVersion () {
		"$major.$minor.$patch"
	}

	/**
	 * Returns the full version string, including GA and snapshot indicators if applicable.
	 * @return String representation of the full version.
	 */
	String getVersion () {
		String baseVersion = getBaseVersion()
		if (ga != null) {
			baseVersion += getGa()
		}
		if (snap) {
			String buildDate = new Date().format('yyyyMMddHHmmss')
			"$baseVersion-$buildDate-SNAPSHOT"
		}
		else {
			"$baseVersion"
		}
	}

	/**
	 * Returns the qualified version symbol, appending "-SNAPSHOT" if the snap flag is true.
	 * @return The qualified version symbol.
	 */
	String getQualifiedVersionSymbol () {
		String qualifier = (snap) ? "-SNAPSHOT" : ""
		getBaseVersion() + qualifier
	}

	/**
	 * Returns the version as an integer, calculated from the major, minor, patch, and GA components.
	 * @return Integer representation of the version.
	 * @throws IllegalStateException If any version component is out of the defined range.
	 */
	Integer getVersionAsInteger () {
		if (ga == null) {
			ga = 0
		}
		return major * 10000000 + minor * 100000 + patch * 1000 + ga
	}

	/**
	 * Validates that the version components are within the expected ranges.
	 * @param major Major version, expected between 0 and 99.
	 * @param minor Minor version, expected between 0 and 99.
	 * @param patch Patch version, expected between 0 and 99.
	 * @param ga Optional GA version, expected between 0 and 999.
	 * @throws IllegalArgumentException If any component is out of range.
	 */
	private void validateVersionComponents (Integer major, Integer minor, Integer patch, Integer ga) {
		if (major == null || major < 0 || major > 99) {
			throw new IllegalArgumentException("Major version must be between 0 and 99.")
		}
		if (minor == null || minor < 0 || minor > 99) {
			throw new IllegalArgumentException("Minor version must be between 0 and 99.")
		}
		if (patch == null || patch < 0 || patch > 99) {
			throw new IllegalArgumentException("Patch version must be between 0 and 99.")
		}
		if (ga != null && (ga < 0 || ga > 999)) {
			throw new IllegalArgumentException("GA version must be between 0 and 999 if specified.")
		}
	}
}
