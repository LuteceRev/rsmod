package org.rsmod.plugins.api.privilege

import org.rsmod.game.privilege.Privilege
import org.rsmod.game.privilege.PrivilegeMap

val privileges: PrivilegeMap by inject()

privileges.register(Privileges.Mod)
privileges.register(Privileges.Admin)

fun PrivilegeMap.register(privilege: Privilege) {
    this[privilege.nameId] = privilege
}
