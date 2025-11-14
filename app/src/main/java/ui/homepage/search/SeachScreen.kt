package ui.homepage.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Data class for search results
data class SearchItem(
    val id: String,
    val title: String,
    val description: String,
    val type: String, // "Reports", "Cases", "Events", "Locations", "Statistics"
    val subtype: String? = null,
    val timestamp: String? = null,
    val priority: String? = null
) {
    val typeColor: Color
        get() = when (type) {
            "Reports" -> Color(0xFF2196F3)    // Blue
            "Cases" -> Color(0xFFF44336)      // Red
            "Events" -> Color(0xFF4CAF50)     // Green
            "Locations" -> Color(0xFF9C27B0)  // Purple
            "Statistics" -> Color(0xFFFF9800) // Orange
            else -> Color(0xFF757575)         // Gray
        }

    val typeBackgroundColor: Color
        get() = typeColor.copy(alpha = 0.1f)

    val priorityColor: Color
        get() = when (priority) {
            "Urgent" -> Color(0xFFD32F2F)
            "High" -> Color(0xFFF57C00)
            "Normal" -> Color(0xFF388E3C)
            else -> Color(0xFF757575)
        }
}

// ViewModel for search functionality
class SearchViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchItem>>(emptyList())
    val searchResults: StateFlow<List<SearchItem>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    // Mock data
    private val mockData = listOf(
        SearchItem(
            id = "1",
            title = "Traffic Report M1",
            description = "Road accident on M1 near Area 18 intersection",
            type = "Reports",
            subtype = "Traffic",
            timestamp = "2024-01-15 14:30",
            priority = "High"
        ),
        SearchItem(
            id = "2",
            title = "Missing Person - John Doe",
            description = "25-year-old male reported missing last seen in Blantyre",
            type = "Cases",
            subtype = "Missing Person",
            timestamp = "2024-01-14 09:15",
            priority = "Urgent"
        ),
        SearchItem(
            id = "3",
            title = "Community Policing Meeting",
            description = "Monthly community policing meeting at Lilongwe Station",
            type = "Events",
            subtype = "Meeting",
            timestamp = "2024-01-20 10:00",
            priority = "Normal"
        ),
        SearchItem(
            id = "4",
            title = "Lilongwe Central Police Station",
            description = "Main police station serving Lilongwe central area",
            type = "Locations",
            subtype = "Station",
            timestamp = null,
            priority = null
        ),
        SearchItem(
            id = "5",
            title = "Q3 2024 Crime Statistics",
            description = "Quarterly crime statistics report for Lilongwe region",
            type = "Statistics",
            subtype = "Report",
            timestamp = "2024-01-10",
            priority = null
        ),
        SearchItem(
            id = "6",
            title = "Burglary Case - Area 43",
            description = "Residential burglary reported in Area 43",
            type = "Cases",
            subtype = "Burglary",
            timestamp = "2024-01-13 22:45",
            priority = "High"
        )
    )

    init {
        // Observe search query changes with debounce
        _searchQuery
            .debounce(300L)
            .distinctUntilChanged()
            .onEach { query ->
                performSearch(query)
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
    }

    fun addToSearchHistory(query: String) {
        if (query.isNotBlank()) {
            val currentHistory = _searchHistory.value.toMutableList()
            currentHistory.remove(query)
            currentHistory.add(0, query)
            _searchHistory.value = currentHistory.take(5) // Keep last 5 searches
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _isLoading.value = false
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Simulate API call delay
                delay(500)
                val results = if (query.isNotEmpty()) {
                    mockData.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.description.contains(query, ignoreCase = true) ||
                                it.type.contains(query, ignoreCase = true) ||
                                it.subtype?.contains(query, ignoreCase = true) == true
                    }
                } else {
                    emptyList()
                }
                _searchResults.value = results
                if (results.isNotEmpty()) {
                    addToSearchHistory(query)
                }
            } catch (e: Exception) {
                // Handle error
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearSearchHistory() {
        _searchHistory.value = emptyList()
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val viewModel = remember { SearchViewModel() }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search Bar
        SearchBarSection(
            searchQuery = searchQuery,
            onQueryChange = viewModel::updateSearchQuery,
            onClearClick = {
                viewModel.clearSearch()
                keyboardController?.hide()
            },
            onSearch = {
                keyboardController?.hide()
            },
            focusRequester = focusRequester,
            modifier = Modifier.padding(16.dp)
        )

        // Content based on state
        when {
            isLoading -> {
                LoadingState(modifier = Modifier.weight(1f))
            }
            searchQuery.isEmpty() -> {
                EmptyState(
                    searchHistory = searchHistory,
                    onHistoryItemClick = viewModel::updateSearchQuery,
                    onClearHistory = viewModel::clearSearchHistory,
                    modifier = Modifier.weight(1f)
                )
            }
            searchResults.isEmpty() -> {
                NoResultsState(
                    searchQuery = searchQuery,
                    modifier = Modifier.weight(1f)
                )
            }
            else -> {
                SearchResultsList(
                    results = searchResults,
                    onItemClick = { item ->
                        navigateToDetail(navController, item)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    // Auto-focus search field when screen loads
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarSection(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearch: () -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        TextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                Text(
                    "Search reports, cases, locations...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = onClearClick,
                        modifier = Modifier.semantics {
                            contentDescription = "Clear search query"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            singleLine = true
        )
    }
}

@Composable
private fun EmptyState(
    searchHistory: List<String>,
    onHistoryItemClick: (String) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Search Police Database",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Find reports, cases, events, and locations",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Search History
        if (searchHistory.isNotEmpty()) {
            Spacer(modifier = Modifier.height(32.dp))
            SearchHistorySection(
                history = searchHistory,
                onItemClick = onHistoryItemClick,
                onClearHistory = onClearHistory
            )
        }
    }
}

@Composable
private fun SearchHistorySection(
    history: List<String>,
    onItemClick: (String) -> Unit,
    onClearHistory: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Searches",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(onClick = onClearHistory) {
                Text("Clear")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(history) { query ->
                SearchHistoryItem(
                    query = query,
                    onClick = { onItemClick(query) }
                )
            }
        }
    }
}

@Composable
private fun SearchHistoryItem(
    query: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = query,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun NoResultsState(
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No results found for \"$searchQuery\"",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Try different keywords or check spelling",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SearchResultsList(
    results: List<SearchItem>,
    onItemClick: (SearchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(results, key = { it.id }) { item ->
            SearchResultItem(
                item = item,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun SearchResultItem(
    item: SearchItem,
    onItemClick: (SearchItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with title and priority
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                item.priority?.let { priority ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = item.priorityColor.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = priority,
                            style = MaterialTheme.typography.labelSmall,
                            color = item.priorityColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Footer with type and timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Type tag
                Box(
                    modifier = Modifier
                        .background(
                            color = item.typeBackgroundColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.type,
                        style = MaterialTheme.typography.labelSmall,
                        color = item.typeColor,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Timestamp
                item.timestamp?.let { timestamp ->
                    Text(
                        text = timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun navigateToDetail(navController: NavController, item: SearchItem) {
    // Define your navigation routes here
    when (item.type) {
        "Reports" -> navController.navigate("report_detail/${item.id}")
        "Cases" -> navController.navigate("case_detail/${item.id}")
        "Events" -> navController.navigate("event_detail/${item.id}")
        "Locations" -> navController.navigate("location_detail/${item.id}")
        "Statistics" -> navController.navigate("statistics_detail/${item.id}")
    }
}

