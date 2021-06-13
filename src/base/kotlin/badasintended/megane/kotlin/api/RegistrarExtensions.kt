@file:Suppress("HasPlatformType")

package badasintended.megane.kotlin.api

import badasintended.megane.api.provider.*
import badasintended.megane.api.registry.MeganeClientRegistrar
import badasintended.megane.api.registry.MeganeRegistrar
import kotlin.reflect.KClass

fun <T : Any> MeganeRegistrar.energy(priority: Int, clazz: KClass<T>, provider: EnergyProvider<T>) =
    energy(priority, clazz.java, provider)

fun <T : Any> MeganeRegistrar.energy(clazz: KClass<T>, provider: EnergyProvider<T>) =
    energy(clazz.java, provider)

fun <T : Any> MeganeRegistrar.fluid(priority: Int, clazz: KClass<T>, provider: FluidProvider<T>) =
    fluid(priority, clazz.java, provider)

fun <T : Any> MeganeRegistrar.fluid(clazz: KClass<T>, provider: FluidProvider<T>) =
    fluid(clazz.java, provider)

fun <T : Any> MeganeRegistrar.inventory(priority: Int, clazz: KClass<T>, provider: InventoryProvider<T>) =
    inventory(priority, clazz.java, provider)

fun <T : Any> MeganeRegistrar.inventory(clazz: KClass<T>, provider: InventoryProvider<T>) =
    inventory(clazz.java, provider)

fun <T : Any> MeganeRegistrar.progress(priority: Int, clazz: KClass<T>, provider: ProgressProvider<T>) =
    progress(priority, clazz.java, provider)

fun <T : Any> MeganeRegistrar.progress(clazz: KClass<T>, provider: ProgressProvider<T>) =
    progress(clazz.java, provider)

fun <T : Any> MeganeClientRegistrar.fluid(clazz: KClass<T>, provider: FluidInfoProvider<T>) =
    fluid(clazz.java, provider)
