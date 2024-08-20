package model

// TODO: I never call the primary constructor?
class Card(var front: String?, var back: String?) {
    constructor() : this(null, null)
}