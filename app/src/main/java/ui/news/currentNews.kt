package ui.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.malawipoliceapp.R
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.primaryColor

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.malawipoliceapp.ui.theme.complementoryColor
import ui.TruncatedText

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CurrentNews() {

    val categories = listOf(
        "All",
        "Crime Prevention",
        "Traffic Updates",
        "Missing Person",
        "Community Alerts",
        "Police Achievements",
        "Press Release",
        "Public Safety",
        "Arrests"
    )

    var selectedCategory by remember { mutableStateOf("All") }


    val filteredList = if (selectedCategory == "All") {
        newsList
    } else {
        newsList.filter { it.category == selectedCategory }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
        //.padding(vertical = 12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.width(14.dp))
            categories.forEach { category ->
                FilterChip(
                    text = category,
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredList) { item ->
                NewsItem(item)
            }
        }
    }
}


@Composable
fun NewsItem(news: NewsData) {
    var expanded by remember { mutableStateOf(false) }
    if (!expanded) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primaryColor.copy(alpha = 0.1f))
                .padding(8.dp)
        ) {
            // IMAGE
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(news.photos.first()),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

            }

            // TEXT
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 12.dp)
            ) {
                TruncatedText(
                    text = news.headline,
                    maxChar = 20,
                    textFontWeight = FontWeight.Black,
                    textColor = Black,
                    textFontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier.clickable {
                        expanded = true
                    },
                    text = news.summary,
                            maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                    //.background(Black),
                    //verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.height(20.dp)
                            .fillMaxHeight(0.5f)
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        //view
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = "views",
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "${news.views} views",
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        //reaction
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                contentDescription = "reactions",
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "20k reaction",
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            )
                        }

                    }

                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //profile container
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .border(
                            width = 2.dp,
                            color = White,
                            shape=RoundedCornerShape(50.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile_picture),
                        contentDescription = "profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier=Modifier.width(20.dp))
                Column(
                    modifier=Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight()
                ){
                    Text(
                        text = "Malawi police news",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize=18.sp,
                        color = primaryColor,
                        maxLines = 1,
                    )
                    Spacer(modifier= Modifier.height(2.dp))
                    Text(
                        text = "By ${news.author}",
                        fontWeight = FontWeight.Medium,
                        fontSize=14.sp,
                        maxLines = 1,
                    )
                    Spacer(modifier= Modifier.height(2.dp))
                    Text(
                        text = news.createdDate,
                        fontWeight = FontWeight.Normal,
                        fontSize=12.sp,
                        maxLines = 1,
                    )



                }
            }
            Spacer(modifier= Modifier.height(10.dp))
            Text(
                text = news.headline,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier= Modifier.height(10.dp))
            Text(
                text = news.content,
                fontWeight = FontWeight.Normal,
                fontSize=16.sp, modifier = Modifier.clickable {
                    expanded = false
                }
            )

            Spacer(modifier= Modifier.height(10.dp))
            news.photos.dropLast(1).forEach { photoRes ->
                NewsPhotos(photoRes)

                Spacer(modifier = Modifier.height(30.dp))
            }


            LastNewsImage(news.photos.last())

            Spacer(modifier= Modifier.height(50.dp))
        }
    }

}


@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center, // <-- centers content vertically & horizontally
        modifier = Modifier
            .background(
                color = if (selected) complementoryColor else Color.Transparent,
                shape = RoundedCornerShape(50.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 3.dp)
            .then(
                if (text == "All") Modifier.width(50.dp)
                else Modifier  // dynamic width
            ),
    ) {
        Text(
            text = text,
            color = if (selected) primaryColor else Black,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 14.sp
        )
    }

}


val newsList = listOf(

    NewsData(
        headline = "Armed Robbery Foiled in Lilongwe Market",
        summary = "Police on early-morning patrol foiled an armed robbery at Lilongwe Central Market after confronting a gang carrying homemade weapons…",
        content = "Police in Lilongwe successfully foiled an armed robbery attempt at Lilongwe Central Market early Monday morning. According to Officer Banda, patrol officers noticed suspicious movements around closed stalls shortly after 3 a.m. \n\nWhen approached, the armed gang scattered, abandoning two homemade firearms, a panga knife, and masks. No injuries were reported. Investigators suspect the gang is linked to recent market break-ins and are reviewing CCTV footage to identify the suspects. \n\nPolice urge traders to increase vigilance and report suspicious activity, especially during late-night and early-morning hours.",
        category = "Arrests",
        author = "Officer Banda",
        createdDate = "2025-11-20",
        updatedAt = "",
        isUrgent = true,
        views = 120,
        taggedEntities = "Traffic",
        photos = listOf(
            R.drawable.police_unit,
            R.drawable.policesniper,
            R.drawable.spec_ops,
        )
    ),

    NewsData(
        headline = "Motor Vehicle Theft Ring Busted",
        summary = "A coordinated police operation in Blantyre and Zomba led to the arrest of four suspects linked to a major vehicle theft syndicate…",
        content = "Police in the Southern Region have dismantled a motor vehicle theft syndicate operating between Blantyre and Zomba. Following a week-long intelligence operation, officers raided a rented house in Chilobwe Township where the suspects were allegedly dismantling stolen vehicles. \n\nRecovered items include master keys, fake number plates, fuel cards, and several stripped vehicle parts. The four suspects, aged 24–38, are believed to have supplied stolen parts to black-market dealers. \n\nPolice say more arrests are expected as investigations continue and urge motorists to use security devices such as alarms and steering locks.",
        category = "Police Achievements",
        author = "Admin",
        createdDate = "2025-11-21",
        updatedAt = "",
        isUrgent = false,
        views = 32,
        taggedEntities = "General",
        photos = listOf(
            R.drawable.motovehicletheft,
        )
    ),

    NewsData(
        headline = "Night patrols",
        summary = "Enhanced night patrols in the CBD have led to a significant drop in crime, boosting safety for residents and businesses…",
        content = "Police statistics show a significant drop in crime within the central business district following intensified night patrols over the past three months. Officers have increased foot and vehicle patrols, focusing on areas previously identified as hotspots for theft and disorderly conduct. \n\nBusiness owners and residents have praised the initiative, reporting improved safety around bus terminals, ATMs, and entertainment venues. Police credit the success to coordinated patrols, community engagement, and quicker response times. \n\nAuthorities encourage continued cooperation and urge communities to sustain the progress by reporting suspicious activity.",
        category = "Police Achievements",
        author = "Admin",
        createdDate = "2025-11-21",
        updatedAt = "",
        isUrgent = false,
        views = 32,
        taggedEntities = "General",
        photos = listOf(
            R.drawable.roadblock,
            R.drawable.patrols,
        )
    ),

    NewsData(
        headline = "New Recruitment Drive Announced",
        summary = "The Malawi Police Service has opened recruitment for 500 new officers as part of nationwide security strengthening efforts…",
        content = "The Malawi Police Service has announced the recruitment of 500 new officers across the country. The initiative aims to boost manpower, improve rapid-response operations, and support specialised units such as cybercrime, traffic enforcement, and forensic investigations. \n\nThe recruitment is open to Malawians aged 18–25 who meet the entry qualifications. Successful applicants will undergo intensive training at various police training schools. \n\nAuthorities warn the public to avoid fake recruitment agents and follow official communication channels for accurate information and application procedures.",
        category = "Police Achievements",
        author = "Admin",
        createdDate = "2025-11-21",
        updatedAt = "",
        isUrgent = false,
        views = 32,
        taggedEntities = "General",
        photos = listOf(
            R.drawable.newrecruitment,
            R.drawable.recruitment2,
        )
    ),

    )

@Preview(showBackground = true)
@Composable
fun render() {
    CurrentNews()
}