package tech.esystems.workspace.java.plugin.artifact.extensions

import org.gradle.api.Project
import org.gradle.api.provider.*
import tech.esystems.workspace.java.plugin.artifact.internal.Artifact

import javax.inject.Inject

import static ArtifactExtensionKeys.ARTIFACT_EXTENSION_NAME

/**
 * Extension class for managing artifact details in Gradle, allowing configuration of artifact ID, group ID,
 * and optional classifier. Provides methods to access artifact-related data and create artifact instances.
 */
class ArtifactExtension {

	/** The artifact ID property, representing the unique identifier for the artifact within the project. */

	Property<String> artifactId

	/** The group ID property, representing the organization or group associated with the artifact. */

	Property<String> groupId

	/** The optional classifier property for differentiating variants of the artifact. */

	Property<String> classifier

	/** The computed artifact name, derived from the group and artifact IDs. */

	Provider<String> artifactName

	/** The associated project in which this extension operates. */

	Project project

	/**
	 * Constructs the ArtifactExtension, initializing properties and setting default values
	 * based on project configuration, such as plugin application.
	 *
	 * @param project The Gradle project this extension is associated with.
	 */
	@Inject
	ArtifactExtension (Project project) {
		artifactId = project.objects.property(String)
		groupId = project.objects.property(String)
		classifier = project.objects.property(String)

		groupId.convention(project.provider { getEffectiveGroupId(project) })

		artifactName = artifactId.<String> map { String it ->
			groupId.get() + "." + it
		}

		if (project.plugins.hasPlugin('war')) {
			artifactId.convention(project.provider { (String) project.war.archiveBaseName.get() })
		}
		else if (project.plugins.hasPlugin('java')) {
			artifactId.convention(project.provider { (String) project.jar.archiveBaseName.get() })
		}
		else {
			artifactId.convention(project.provider { project.name })
		}

		this.project = project
	}

	/**
	 * Returns the effective group ID for a given project.
	 * If the project's group matches the default group (derived from the project's relative path),
	 * the function traverses up to the parent project until it reaches the root project.
	 * If the root project's group is also the default, it returns the root project name.
	 *
	 * @param project the project for which the effective group ID is being determined
	 * @return the effective group ID as a String
	 */
	private String getEffectiveGroupId (Project project) {
		def rootProject = project.rootProject

		if (project == rootProject) {
			if (project.group.toString() == "") {
				return project.name
			}
			else {
				return project.group.toString()
			}
		}

		//https://github.com/gradle/gradle/blob/master/subprojects/core/src/main/java/org/gradle/api/internal/project/DefaultProject.java
		def defaultGroup = rootProject.getName() + (project.getParent() == rootProject ? "" : "." + project.getParent().getPath().substring(1).replace(':', '.'))

		if (project.group.toString() == defaultGroup) {
			return getEffectiveGroupId(project.parent)
		}
		else {
			return project.group.toString()
		}
	}

	/**
	 * Retrieves the provider for the ArtifactExtension associated with the specified project.
	 *
	 * @param project The project for which the extension provider is retrieved.
	 * @return The provider of ArtifactExtension.
	 */
	static Provider<ArtifactExtension> getArtifactExtensionProvider (Project project) {
		project.provider({ (ArtifactExtension) project.extensions.findByName(ARTIFACT_EXTENSION_NAME) })
	}

	/**
	 * Creates and retrieves an Artifact instance based on current extension properties in the project.
	 *
	 * @param project The project from which to retrieve artifact details.
	 * @return An Artifact instance populated with project properties.
	 */
	static Artifact getArtifact (Project project) {
		getArtifactExtensionProvider(project).get().getArtifact()
	}

	/**
	 * Generates an Artifact instance using the current properties of this extension.
	 *
	 * @return A new Artifact instance with the configured values.
	 */
	Artifact getArtifact () {
		if (!artifactId.isPresent()) {
			throw new IllegalStateException("missing artifact id")
		}
		if (!groupId.isPresent()) {
			throw new IllegalStateException("missing group id")
		}
		if (!artifactName.isPresent()) {
			throw new IllegalStateException("missing artifact name")
		}
		new Artifact(artifactId.get(), groupId.get(), artifactName.get(), classifier.getOrNull())
	}

	/**
	 * Sets the artifact ID property.
	 *
	 * @param artifactId The property containing the artifact ID.
	 */
	void setArtifactId (Property<String> artifactId) {
		this.artifactId = artifactId
	}

	/**
	 * Sets the group ID property.
	 *
	 * @param groupId The property containing the group ID.
	 */
	void setGroupId (Property<String> groupId) {
		this.groupId = groupId
	}

	/**
	 * Sets the classifier property.
	 *
	 * @param classifier The property containing the classifier.
	 */
	void setClassifier (Property<String> classifier) {
		this.classifier = classifier
	}

	/**
	 * Sets the artifact ID with a specified value.
	 *
	 * @param value The value to assign to artifact ID.
	 */
	void setArtifactId (String value) {
		artifactId.set(value)
	}

	/**
	 * Sets the group ID with a specified value.
	 *
	 * @param value The value to assign to group ID.
	 */
	void setGroupId (String value) {
		groupId.set(value)
	}

	/**
	 * Sets the classifier with a specified value.
	 *
	 * @param value The value to assign to classifier.
	 */
	void setClassifier (String value) {
		classifier.set(value)
	}
}
