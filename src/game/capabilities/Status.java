package game.capabilities;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    TALL, // use this status to tell that current instance has "grown".
    INVINCIBLE, //for use with magical items
    PROVOKED, //for use for Goombas/ Koopas, tells if they have been engaged in a fight
    HIDE, // for use Koopas, tells if they are hiding in their shells.
    FIREATTACK, //for use when player can attack with fire after consuming fire flower.
}
