package badasintended.megane.kotlin.api

import badasintended.megane.api.MeganeModule
import badasintended.megane.api.registry.MeganeClientRegistrar
import badasintended.megane.api.registry.MeganeRegistrar

@Suppress("HasPlatformType")
interface KMeganeModule : MeganeModule {

    fun MeganeRegistrar.common() {}
    fun MeganeClientRegistrar.client() {}

    @Deprecated(message = "", level = DeprecationLevel.HIDDEN)
    override fun register(registrar: MeganeRegistrar) {
        registrar.common()
    }

    @Deprecated(message = "", level = DeprecationLevel.HIDDEN)
    override fun registerClient(registrar: MeganeClientRegistrar) {
        registrar.client()
    }

}