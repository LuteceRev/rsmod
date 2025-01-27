package org.rsmod.plugins.content.gameframe.util

import org.rsmod.plugins.api.cache.type.enums.EnumType
import org.rsmod.plugins.api.model.ui.StandardGameframe
import org.rsmod.plugins.types.NamedComponent

public object GameframeUtil {

    public fun build(
        reference: StandardGameframe,
        componentEnum: EnumType<NamedComponent, NamedComponent>
    ): Iterable<NamedComponent> {
        val overlays = mutableListOf<NamedComponent>()
        reference.overlays.forEach { targetRef ->
            val overlayRef = reference.topLevel.child(targetRef.child)
            val mappedComponent = componentEnum[overlayRef] ?: return@forEach
            overlays += targetRef.parent().child(mappedComponent.child)
        }
        return overlays
    }
}
