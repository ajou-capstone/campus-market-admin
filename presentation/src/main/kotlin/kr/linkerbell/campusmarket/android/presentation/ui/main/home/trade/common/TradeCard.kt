import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import kotlinx.datetime.LocalDateTime
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade
import kr.linkerbell.campusmarket.android.presentation.common.theme.Black
import kr.linkerbell.campusmarket.android.presentation.common.theme.Blue400
import kr.linkerbell.campusmarket.android.presentation.common.theme.Body1
import kr.linkerbell.campusmarket.android.presentation.common.theme.Caption2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray200
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray300
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline3
import kr.linkerbell.campusmarket.android.presentation.common.theme.White
import kr.linkerbell.campusmarket.android.presentation.common.view.image.PostImage
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.common.TradeItemStatus

@Composable
internal fun TradeCard(
    item: Trade,
    onItemCardClicked: (Long) -> Unit
) {
    val backgroundColor = if (item.isDeleted) Gray300 else White
    val alphaValue = if (item.isDeleted) 0.3f else 1.0f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .alpha(alphaValue)
            .clickable {
                onItemCardClicked(item.itemId)
            },
    ) {
        PostImage(
            data = item.thumbnail,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Column(
            modifier = Modifier.height(100.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.title,
                style = Headline3,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                text = item.nickname,
                style = Caption2,
                color = Black
            )
            Column {
                Text(
                    text = "${item.chatCount} 명이 대화중",
                    style = Caption2,
                    color = Black
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TradeItemStatus(isSold = item.itemStatus == "SOLDOUT")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "${DecimalFormat("#,###").format(item.price)} 원",
                        style = Body1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(min = 100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = creationOrModifiedDate(item),
                        style = Caption2,
                        color = Black
                    )
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val favIcon =
                            if (item.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                        Icon(
                            imageVector = favIcon,
                            tint = Blue400,
                            contentDescription = "isLike",
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(item.likeCount.toString())
                    }
                }
            }
        }
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = Gray200,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}


private fun creationOrModifiedDate(item: Trade): String {
    return if (item.createdDate == item.lastModifiedDate)
        "작성 일자 : ${item.createdDate.date}"
    else
        "최종 수정 일자 : ${item.lastModifiedDate.date}"
}

@Preview
@Composable
private fun SummarizedTradeCardPreview() {

    Column(
        modifier = Modifier.background(White)
    ) {
        TradeCard(
            item = Trade(
                campusId = 1L,
                campusRegion = "서울",
                chatCount = 5,
                isLiked = true,
                itemId = 1001L,
                itemStatus = "AVAILABLE",
                likeCount = 10,
                nickname = "홍길동",
                price = 15000,
                thumbnail = "https://example.com/thumbnail.jpg",
                title = "판매 중인 상품",
                universityName = "서울대학교",
                userId = 123L,
                createdDate = LocalDateTime(2000, 1, 1, 0, 0, 0),
                lastModifiedDate = LocalDateTime(2000, 1, 1, 0, 0, 0),
                isDeleted = false
            ),
            onItemCardClicked = {}
        )
        TradeCard(
            item = Trade(
                campusId = 2L,
                campusRegion = "서울",
                chatCount = 5,
                isLiked = true,
                itemId = 1001L,
                itemStatus = "AVAILABLE",
                likeCount = 10,
                nickname = "홍길동",
                price = 15000,
                thumbnail = "https://example.com/thumbnail.jpg",
                title = "판매 중인 상품",
                universityName = "서울대학교",
                userId = 123L,
                createdDate = LocalDateTime(2000, 1, 1, 0, 0, 0),
                lastModifiedDate = LocalDateTime(2000, 1, 1, 0, 0, 0),
                isDeleted = true
            ),
            onItemCardClicked = {}
        )
    }

}
