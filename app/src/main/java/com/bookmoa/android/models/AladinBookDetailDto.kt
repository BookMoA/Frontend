import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// XML의 루트 태그가 "object" 이므로 이를 감싸는 데이터 클래스
@Parcelize
data class BookDetailResponse(
    @SerializedName("item") val items: List<BookDetail>
) : Parcelable

// "item" 태그 내부의 데이터 클래스
@Parcelize
data class BookDetail(
    @SerializedName("itemId") val id: Long = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("cover") val img: String = "",
    @SerializedName("author") val author: String = "",
    @SerializedName("isbn") val isbn: String = "",
    @SerializedName("isbn13") val isbn13: String="",
    @SerializedName("publisher") val publisher: String = "",
    @SerializedName("subInfo") val subInfo: SubInfo? = null
) : Parcelable

// "subInfo" 태그를 위한 데이터 클래스
@Parcelize
data class SubInfo(
    @SerializedName("subTitle") val subTitle: String = "",
    @SerializedName("originalTitle") val originalTitle: String = "",
    @SerializedName("itemPage") val itemPage: String = "",
) : Parcelable