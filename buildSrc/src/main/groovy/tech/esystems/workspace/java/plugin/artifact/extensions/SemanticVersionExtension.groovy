package tech.esystems.workspace.java.plugin.artifact.extensions

import org.gradle.api.Project
import org.gradle.api.provider.*
import tech.esystems.workspace.java.plugin.artifact.internal.SemanticVersion

import javax.inject.Inject

import static tech.esystems.workspace.java.plugin.artifact.extensions.ArtifactExtensionKeys.SEMANTIC_EXTENSION_NAME

class SemanticVersionExtension {

	Property<Integer> major

	Property<Integer> minor

	Property<Integer> patch

	Property<Boolean> snap

	Project project

	SemanticVersion versionValue

	@Inject
	SemanticVersionExtension (Project project) {
		major = project.objects.property(Integer)

		patch = project.objects.property(Integer)
		minor = project.objects.property(Integer)

		snap = project.objects.property(Boolean)
		snap.set(false)

		versionValue = new SemanticVersion()

		this.project = project
	}

	static Provider<SemanticVersionExtension> getSemanticVersionExtensionProvider (Project project) {
		project.providers.provider({ (SemanticVersionExtension) project.extensions.findByName(SEMANTIC_EXTENSION_NAME) })
	}

	static SemanticVersion getVersionValue (Project project) {
		getSemanticVersionExtensionProvider(project).get().getVersionValue()
	}

	SemanticVersion getVersionValue () {
		versionValue
	}

	void setMajor (Property<Integer> value) {
		if (value.isPresent()) {
			setMajor(value.get())
		}
	}

	@Deprecated
	void setMajorValue (int value) {
		major.set(value)
		getVersionValue().setMajor(value)
	}

	void setMajor (Integer value) {
		major.set(value)
		getVersionValue().setMajor(value)
	}

	void setMinor (Integer value) {
		minor.set(value)
		getVersionValue().setMinor(value)
	}

	void setPatch (Integer value) {
		patch.set(value)
		getVersionValue().setPatch(value)
	}

	void setSnap (Boolean value) {
		snap.set(value)
		getVersionValue().setSnap(value)
	}

	void setMinor (Property<Integer> value) {
		if (value.isPresent()) {
			minor.set(value)
			getVersionValue().setMinor(value.get())
		}
	}

	void setPatch (Property<Integer> value) {
		if (value.isPresent()) {
			patch.set(value)
			getVersionValue().setPatch(value.get())
		}
	}

	void setSnap (Property<Boolean> value) {
		if (value.isPresent()) {
			snap.set(value)
			getVersionValue().setSnap(value.get())
		}
	}
}
