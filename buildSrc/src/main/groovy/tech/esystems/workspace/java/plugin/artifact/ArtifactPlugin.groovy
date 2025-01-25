/**
 * Plugin to manage artifact publication for all subprojects within a Gradle build.
 * Applies Maven publication and configures artifact details and semantic versioning.
 */
package tech.esystems.workspace.java.plugin.artifact

import org.gradle.api.*
import tech.esystems.workspace.java.plugin.artifact.extensions.*
import tech.esystems.workspace.java.plugin.artifact.internal.*

import static tech.esystems.workspace.java.plugin.artifact.extensions.ArtifactExtensionKeys.*

/**
 * The ArtifactPlugin class implements Plugin<Project> to enable
 * the configuration and publication of artifact, including Maven publication,
 * semantic versioning, and artifact metadata.
 */
class ArtifactPlugin implements Plugin<Project> {

	/**
	 * Applies the ArtifactPlugin to all subprojects containing the 'java' plugin.
	 * Configures semantic versioning, artifact details, and Maven publication settings.
	 *
	 * @param target The root project to which this plugin is applied.
	 */
	@Override
	void apply (Project target) {
		def rootProject = target.rootProject

		rootProject.subprojects { Project project ->
			project.pluginManager.withPlugin('java', {

				project.extensions.create(SEMANTIC_EXTENSION_NAME, SemanticVersionExtension, project)
				project.extensions.create(ARTIFACT_EXTENSION_NAME, ArtifactExtension, project)

				project.afterEvaluate {
					SemanticVersion semanticVersion = SemanticVersionExtension.getVersionValue(project)
					Artifact archive = ArtifactExtension.getArtifact(project)

					project.version = semanticVersion.version
					project.group = archive.groupId

					project.ext.set('artifactName', archive.artifactName)

					if (project.plugins.hasPlugin('war')) {
						project.war.archiveBaseName = "${ archive.artifactId }"
						project.war.archiveFileName = "${ archive.artifactName }-${ semanticVersion.qualifiedVersionSymbol }.war"
					}
					else {
						project.jar.archiveBaseName = "${ archive.artifactId }"
						project.jar.archiveFileName = "${ archive.artifactName }-${ semanticVersion.qualifiedVersionSymbol }.jar"
					}
				}
			})
		}
	}
}
