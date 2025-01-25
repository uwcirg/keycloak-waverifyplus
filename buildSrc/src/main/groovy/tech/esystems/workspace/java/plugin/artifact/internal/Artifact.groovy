/**
 * Represents an artifact to be published, containing details such as artifact ID,
 * group ID, artifact name, and optional classifier.
 */
package tech.esystems.workspace.java.plugin.artifact.internal

/**
 * The Artifact class holds metadata for a Gradle artifact, including artifact ID,
 * group ID, artifact name, and optional classifier. This metadata is used for
 * identifying and organizing artifact in a publication process.
 */
class Artifact {

	/** The unique ID of the artifact. */

	final String artifactId

	/** The group ID associated with the artifact. */

	final String groupId

	/** The name of the artifact, used in publication. */

	final String artifactName

	/** An optional classifier to distinguish variations of the artifact. */

	final String classifier

	/**
	 * Constructor for Artifact without a classifier.
	 *
	 * @param artifactId The unique ID of the artifact.
	 * @param groupId The group ID associated with the artifact.
	 * @param artifactName The name of the artifact.
	 */
	Artifact (String artifactId, String groupId, String artifactName) {
		this(artifactId, groupId, artifactName, null)
	}

	/**
	 * Constructor for Artifact with a classifier.
	 *
	 * @param artifactId The unique ID of the artifact.
	 * @param groupId The group ID associated with the artifact.
	 * @param artifactName The name of the artifact.
	 * @param classifier An optional classifier to distinguish variations of the artifact.
	 */
	Artifact (String artifactId, String groupId, String artifactName, String classifier) {
		this.artifactId = artifactId
		this.groupId = groupId
		this.artifactName = artifactName
		this.classifier = classifier
	}
}
