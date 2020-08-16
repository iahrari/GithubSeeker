package ir.iahrari.githubseeker.service.model.trending

enum class TrendingSince(private val since: String) {
    Daily("daily"),
    Weekly("weekly"),
    Monthly("monthly");

    override fun toString(): String {
        return since
    }
}