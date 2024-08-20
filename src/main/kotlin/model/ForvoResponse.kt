package model

data class ForvoResponse(
    var attributes: Attributes,
    var items: List<Item>
)

data class Attributes(var total: Int)

data class Item(
    var id: Long,
    var word: String,
    var original: String,
    var addtime: String, //TODO: "2009-03-10 03:43:55" - try to change this type to something like TimeStamp / DateTime etc?
    var hits: Long,
    var username: String,
    var sex: String, // TODO: can be 'm' or 'f' - make this an enum?
    var country: String, // "Germany"
    var code: String, // "de"
    var langname: String, // "German"
    var pathmp3: String, // TODO: Map this to URI? "https:\/\/apifree.forvo.com\/audio\/3d2k3e2o1b2a1h2i2b1g273g212h341j222d3e291j3p3c37283n3a333k1b241g352a1n2q3q1j3d1o2c2m231j2h34212a3g2j1p1o2m2l3i3m343l2i27232q2g372b3h2d3d2m271i3c1m3o1n2d2d1i3a3o24311p2438371t1t_2b231p3a382g3527223a1o3e311k3a331m251n351n211t1t",
    var pathogg: String, // TODO: Map this to URI? "https:\/\/apifree.forvo.com\/audio\/2b353d1k2l1g212q37233n371m381b391g21383832252i3l3n213d362j2b2l2e1k2k3o341h263j3e3q35311l3b2n35372134242l1h271o1o3f2d3l2m1m2p2b2c2i26352c342n3h3f1h231b2f2q363q2l1b2d2k2k272h1t1t_282n2k1o2c1n2i1p2p2g2l3p1f393h1f1b2d3q2h27371t1t",
    var rate: Long,
    var num_votes: Long,
    var num_positive_votes: Long
)
