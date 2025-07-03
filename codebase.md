# .gitignore

```
*.iml
.gradle
/local.properties
/.idea/caches
/.idea/libraries
/.idea/modules.xml
/.idea/workspace.xml
/.idea/navEditor.xml
/.idea/assetWizardSettings.xml
.DS_Store
/build
/captures
.externalNativeBuild
.cxx
local.properties

```

# app\.gitignore

```
/build
```

# app\build.gradle.kts

```kts
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "kz.petproject.gts_tz"
    compileSdk = 35

    defaultConfig {
        applicationId = "kz.petproject.gts_tz"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
```

# app\proguard-rules.pro

```pro
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
```

# app\src\androidTest\java\kz\petproject\gts_tz\ExampleInstrumentedTest.kt

```kt
package kz.petproject.gts_tz

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("kz.petproject.gts_tz", appContext.packageName)
    }
}
```

# app\src\main\AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.GTS_TZ"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name"
            android:theme="@style/Theme.GTS_TZ">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

# app\src\main\java\kz\petproject\gts_tz\data\Article.kt

```kt
package kz.petproject.gts_tz.data
/**
 * Represents an Article.
 *
 * @property id The unique identifier for the article.
 * @property title The title of the article.
 * @property content The full text content of the article.
 * @property author The [Author] who wrote the article.
 * @property status The current moderation status ("PUBLISHED", "ON_MODERATION", "REJECTED").
 * @property rejectionReason An optional reason for why the article was rejected. Only present if status is "REJECTED".
 * @property createdAt The timestamp (in milliseconds) when the article was created.
 * @property publishedAt An optional timestamp (in milliseconds) when the article was published.
 */
data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val author: Author,
    val status: String,
    val rejectionReason: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val publishedAt: Long? = null
)
```

# app\src\main\java\kz\petproject\gts_tz\data\Author.kt

```kt
package kz.petproject.gts_tz.data
/**
 * Represents an Author.
 *
 * @property id The unique identifier for the author.
 * @property name The full name of the author.
 */
data class Author(
    val id: Int,
    val name: String
)
```

# app\src\main\java\kz\petproject\gts_tz\data\DummyData.kt

```kt
package kz.petproject.gts_tz.data

import java.util.concurrent.TimeUnit

object DummyData {
    val authors = listOf(
        Author(id = 1, name = "Иван Петров"),
        Author(id = 2, name = "Мария Сидорова")
    )

    // Also add a sample user list to DummyData
// In object DummyData { ... }
    val users = listOf(
        User(1, "Admin User", "admin", "admin123", "ADMIN"),
        User(2, "Иван Петров", "ivan_p", "pass1", "AUTHOR"),
        User(3, "Мария Сидорова", "maria_s", "pass2", "AUTHOR"),
        User(4, "Сергей Васильев", "sergey_v", "pass3", "MODERATOR")
    )
    // A list of sample articles covering all possible statuses.
    val articles = listOf(
        Article(
            id = 1,
            title = "Compose — будущее Android-разработки",
            content = "Jetpack Compose — это современный декларативный набор инструментов для создания нативного пользовательского интерфейса Android. Он упрощает и ускоряет разработку пользовательского интерфейса на Android, позволяя быстро воплощать приложения в жизнь с меньшим количеством кода.",
            author = authors[0],
            status = "PUBLISHED",
            publishedAt = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1) // Published yesterday
        ),
        Article(
            id = 6,
            title = "Идеи для новой статьи",
            content = "Начать с обзора последних...", // Can be empty or partially filled
            author = authors[0], // Belongs to "Иван Петров"
            status = "DRAFT" // The new status
        ),
        Article(
            id = 2,
            title = "Что нового в Kotlin 1.9?",
            content = "В новой версии Kotlin были представлены значительные улучшения производительности компилятора, новые возможности языка и обновление стандартной библиотеки. Рассмотрим ключевые изменения...",
            author = authors[1],
            status = "PUBLISHED",
            publishedAt = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3) // Published 3 days ago
        ),
        Article(
            id = 3,
            title = "KMM для кросс-платформенной разработки",
            content = "Kotlin Multiplatform Mobile (KMM) позволяет использовать общую кодовую базу для бизнес-логики приложений iOS и Android. Это помогает сэкономить время и обеспечить консистентность.",
            author = authors[0],
            status = "ON_MODERATION"
        ),
        Article(
            id = 4,
            title = "Архитектура MVVM",
            content = "MVVM (Model-View-ViewModel) - это...",
            author = authors[1],
            status = "REJECTED",
            rejectionReason = "Статья слишком короткая. Пожалуйста, раскройте тему более подробно."
        ),
        Article(
            id = 5,
            title = "Знакомство с Coroutines",
            content = "Асинхронное программирование — неотъемлемая часть современных приложений. В Kotlin для этих целей существуют корутины, которые позволяют писать асинхронный код последовательно и понятно.",
            author = authors[0],
            status = "ON_MODERATION"
        )
    )

}
```

# app\src\main\java\kz\petproject\gts_tz\data\User.kt

```kt
package kz.petproject.gts_tz.data// In data/Model.kt
/**
 * Represents a user in the system.
 * @property id The unique identifier for the user.
 * @property name The full name of the user.
 * @property login The unique login username.
 * @property password The user's password (in a real app, this would be a hash).
 * @property role The role of the user ("ADMIN", "AUTHOR", "MODERATOR").
 */
data class User(
    val id: Int,
    val name: String,
    val login: String,
    val password: String, // For UI demo purposes. In a real app, never store plain text passwords.
    val role: String
)
```

# app\src\main\java\kz\petproject\gts_tz\MainActivity.kt

```kt
package kz.petproject.gts_tz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kz.petproject.gts_tz.ui.theme.GTS_TZTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GTS_TZTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GTS_TZTheme {
        Greeting("Android")
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\model\ArticleCard.kt

```kt
package kz.petproject.gts_tz.ui.model

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import java.util.Date
import java.util.Locale

private fun formatDate(timestamp: Long?): String {
    if (timestamp == null) return "N/A"
    val date = Date(timestamp)
    val format = SimpleDateFormat("d MMMM yyyy", Locale("ru")) // Example: "15 мая 2023"
    return format.format(date)
}

/**
 * Represents a single news item card in the feed.
 *
 * @param article The article data to display.
 * @param onClick The action to perform when the card is clicked.
 */

@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Автор: ${article.author.name} · ${formatDate(article.publishedAt)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\model\AuthorArticleCard.kt

```kt
package kz.petproject.gts_tz.ui.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.petproject.gts_tz.data.Article

@Composable
fun AuthorArticleCard(
    article: Article,
    onArticleClick: (articleId: Int) -> Unit
) {
    val isClickable = article.status in listOf("REJECTED", "DRAFT")
    
    Card(
        // Only rejected articles can be clicked to be edited.
        onClick = { if (isClickable) onArticleClick(article.id) },
        enabled = isClickable, // This controls the visual feedback (ripple effect)
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatusChip(article.status)
            }
            // If the article was rejected, show the reason.
            if (article.status == "REJECTED") {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Причина: ${article.rejectionReason}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun StatusChip(status: String) {
    val (backgroundColor, textColor) = when (status) {
        "PUBLISHED" -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        "ON_MODERATION" -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        "REJECTED" -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
        "DRAFT" -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    }
    // A mapping for user-friendly status text
    val statusText = when (status) {
        "PUBLISHED" -> "Опубликована"
        "ON_MODERATION" -> "На модерации"
        "REJECTED" -> "Отклонена"
        "DRAFT" -> "Черновик"
        else -> "Неизвестно"
    }

    Text(
        text = statusText,
        color = textColor,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        fontSize = 12.sp
    )
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\presentation\admin\RoleChip.kt

```kt
package kz.petproject.gts_tz.ui.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.data.User

// --- Helper UI Components ---

@Composable
private fun RoleChip(role: String) {
    val (backgroundColor, textColor) = when (role) {
        "ADMIN" -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        "AUTHOR" -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        "MODERATOR" -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    val roleText = when (role) {
        "ADMIN" -> "Админ"
        "AUTHOR" -> "Автор"
        "MODERATOR" -> "Модератор"
        else -> "Неизвестно"
    }

    Text(
        text = roleText,
        color = textColor,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

// --- Main Screen ---

/**
 * The screen for an administrator to manage users.
 *
 * @param users List of all users in the system.
 * @param onCreateUser Lambda triggered to create a new user with a given name and role.
 * @param onNavigateUp Lambda for back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    users: List<User>,
    onCreateUser: (name: String, login: String, password: String, role: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var newUserName by remember { mutableStateOf("") }
    var newUserLogin by remember { mutableStateOf("") }
    var newUserPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val roles = listOf("AUTHOR", "MODERATOR")
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Панель администратора") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) {  paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- User Creation Form ---
            Text("Создать нового пользователя", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = newUserName,
                onValueChange = { newUserName = it },
                label = { Text("Имя и фамилия") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = newUserLogin,
                onValueChange = { newUserLogin = it },
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = newUserPassword,
                onValueChange = { newUserPassword = it },
                label = { Text("Пароль") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Favorite else Icons.Filled.FavoriteBorder
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Показать/скрыть пароль")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedRole?.replaceFirstChar { it.titlecase() } ?: "Выберите роль",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Роль") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    roles.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role.replaceFirstChar { it.titlecase() }) },
                            onClick = {
                                selectedRole = role
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    onCreateUser(newUserName, newUserLogin, newUserPassword, selectedRole!!)
                    // Reset form
                    newUserName = ""
                    newUserLogin = ""
                    newUserPassword = ""
                    selectedRole = null
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = newUserName.isNotBlank() && newUserLogin.isNotBlank() && newUserPassword.isNotBlank() && selectedRole != null
            ) {
                Text("Создать пользователя")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // --- Existing Users List ---
            Text("Существующие пользователи", style = MaterialTheme.typography.titleLarge)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(users, key = { it.id }) { user ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(user.name, style = MaterialTheme.typography.bodyLarge)
                                Text(user.login, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            RoleChip(user.role)
                        }
                    }
                }
            }
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Admin Dashboard", showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    MaterialTheme {
        AdminDashboardScreen(
            users = DummyData.users,
            onCreateUser = { _, _, _, _ -> },
            onNavigateUp = {}
        )
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\presentation\author\AuthorDashboardScreen.kt

```kt
package kz.petproject.gts_tz.ui.presentation.author

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.ui.model.AuthorArticleCard

/**
 * The main screen for an author to view their own articles and statuses.
 *
 * @param authorName The name of the current author.
 * @param authorArticles The list of articles written by this author.
 * @param onArticleClick Lambda for when a (rejected) article is clicked.
 * @param onFabClick Lambda for the "create new post" floating action button.
 * @param onNavigateUp Lambda for back navigation.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorDashboardScreen(
    authorName: String,
    authorArticles: List<Article>,
    onArticleClick: (articleId: Int) -> Unit,
    onFabClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои статьи") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(Icons.Default.Add, contentDescription = "Создать статью")
            }
        }
    ) { paddingValues ->
        if (authorArticles.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Вы еще не написали ни одной статьи.\nНажмите '+' чтобы начать.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(authorArticles, key = { it.id }) { article ->
                    AuthorArticleCard(
                        article = article,
                        onArticleClick = onArticleClick
                    )
                }
            }
        }
    }
}


// --- PREVIEWS ---

@Preview(name = "Author Dashboard - With Posts", showBackground = true)
@Composable
fun AuthorDashboardScreenPreview_WithPosts() {
    MaterialTheme {
        AuthorDashboardScreen(
            authorName = "Иван Петров",
            // We get all articles written by author with id 1
            authorArticles = DummyData.articles.filter { it.author.id == 1 },
            onArticleClick = {},
            onFabClick = {},
            onNavigateUp = {}
        )
    }
}

@Preview(name = "Author Dashboard - Empty State", showBackground = true)
@Composable
fun AuthorDashboardScreenPreview_Empty() {
    MaterialTheme {
        AuthorDashboardScreen(
            authorName = "Новый Автор",
            authorArticles = emptyList(), // No articles yet
            onArticleClick = {},
            onFabClick = {},
            onNavigateUp = {}
        )
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\presentation\author\CreatePostScreen.kt

```kt
package kz.petproject.gts_tz.ui.presentation.author

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A stateless screen for creating or editing an article.
 *
 * @param title The current value for the article title.
 * @param onTitleChange Lambda called when the title text changes.
 * @param content The current value for the article content.
 * @param onContentChange Lambda called when the content text changes.
 * @param isEditing A boolean flag to indicate if the screen is in "edit" mode.
 *                  This changes the UI text (e.g., "New Post" vs "Edit Post").
 * @param onSubmitClick Lambda called when the user clicks the submit button.
 * @param onNavigateUp Lambda for handling back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    isEditing: Boolean,
    onSubmitClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Редактирование статьи" else "Новая статья") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Makes the column scrollable
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Field
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Заголовок") },
                singleLine = true
            )

            // Content Field
            OutlinedTextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp), // Give it a good minimum height
                label = { Text("Текст статьи") }
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            // Submit Button
            Button(
                onClick = onSubmitClick,
                modifier = Modifier.fillMaxWidth(),
                // Button is disabled if title or content is blank
                enabled = title.isNotBlank() && content.isNotBlank()
            ) {
                Text(
                    text = if (isEditing) "Сохранить изменения" else "Отправить на модерацию",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

// --- PREVIEWS ---

/**
 * Stateful preview for creating a new post.
 * This pattern allows you to interact with the UI in the preview pane.
 */
@Preview(name = "Create New Post", showBackground = true)
@Composable
fun CreatePostScreenPreview_New() {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    MaterialTheme {
        CreatePostScreen(
            title = title,
            onTitleChange = { title = it },
            content = content,
            onContentChange = { content = it },
            isEditing = false,
            onSubmitClick = { /* Clicked! */ },
            onNavigateUp = { /* Nav up! */ }
        )
    }
}

/**
 * Stateful preview for editing an existing post.
 */
@Preview(name = "Edit Existing Post", showBackground = true)
@Composable
fun CreatePostScreenPreview_Editing() {
    var title by remember { mutableStateOf("Архитектура MVVM") }
    var content by remember { mutableStateOf("MVVM (Model-View-ViewModel) - это...") }

    MaterialTheme {
        CreatePostScreen(
            title = title,
            onTitleChange = { title = it },
            content = content,
            onContentChange = { content = it },
            isEditing = true,
            onSubmitClick = { /* Clicked! */ },
            onNavigateUp = { /* Nav up! */ }
        )
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\presentation\AuthScreen.kt

```kt
package kz.petproject.gts_tz.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A stateless authentication screen.
 * It is designed to be controlled by a ViewModel.
 *
 * @param login The current value for the login field.
 * @param password The current value for the password field.
 * @param onLoginChange Lambda to be called when the login text changes.
 * @param onPasswordChange Lambda to be called when the password text changes.
 * @param onLoginClick Lambda to be called when the login button is clicked.
 * @param isLoading True if the login process is in progress.
 * @param error An optional error message to display.
 */
@Composable
fun AuthScreen(
    login: String,
    password: String,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean,
    error: String?
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Авторизация",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Login Field
        OutlinedTextField(
            value = login,
            onValueChange = onLoginChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Логин") },
            singleLine = true,
            isError = error != null // Highlight field if there is any error
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Favorite
                else Icons.Filled.FavoriteBorder

                val description = if (passwordVisible) "Скрыть пароль" else "Показать пароль"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            isError = error != null
        )

        // Error Message
        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Login Button or Progress Indicator
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = login.isNotBlank() && password.isNotBlank()
            ) {
                Text("Войти", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Default State", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_Default() {
    MaterialTheme { // Wrap in a theme for proper previewing
        AuthScreen(
            login = "",
            password = "",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = false,
            error = null
        )
    }
}

@Preview(name = "With Input", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_WithInput() {
    MaterialTheme {
        AuthScreen(
            login = "author_1",
            password = "password123",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = false,
            error = null
        )
    }
}

@Preview(name = "Loading State", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_Loading() {
    MaterialTheme {
        AuthScreen(
            login = "author_1",
            password = "password123",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = true,
            error = null
        )
    }
}

@Preview(name = "Error State", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_Error() {
    MaterialTheme {
        AuthScreen(
            login = "author_1",
            password = "wrongpassword",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = false,
            error = "Неверный логин или пароль"
        )
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\presentation\moderator\ArticleReviewScreen.kt

```kt
package kz.petproject.gts_tz.ui.presentation.moderator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData

/**
 * A screen where a moderator can read a full article and approve or reject it.
 *
 * @param article The article to be reviewed.
 * @param onApprove Lambda for the "Approve" action.
 * @param onReject Lambda for the "Reject" action, which takes the reason as a String.
 * @param onNavigateUp Lambda for back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleReviewScreen(
    article: Article,
    onApprove: () -> Unit,
    onReject: (reason: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var showRejectDialog by remember { mutableStateOf(false) }

    if (showRejectDialog) {
        RejectReasonDialog(
            onDismiss = { showRejectDialog = false },
            onConfirm = { reason ->
                showRejectDialog = false
                onReject(reason)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Проверка статьи") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Scrollable content area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(article.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Автор: ${article.author.name}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Divider(Modifier.padding(vertical = 16.dp))
                Text(article.content, style = MaterialTheme.typography.bodyLarge)
            }

            // Action buttons at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { showRejectDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Отклонить")
                }
                Button(
                    onClick = onApprove,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Одобрить")
                }
            }
        }
    }
}

// --- Helper Component: Rejection Dialog ---

/**
 * A dialog for entering the reason for rejecting an article.
 *
 * @param onDismiss Lambda called when the dialog is dismissed (e.g., by clicking outside or cancel).
 * @param onConfirm Lambda called with the reason text when the confirm button is clicked.
 */
@Composable
private fun RejectReasonDialog(
    onDismiss: () -> Unit,
    onConfirm: (reason: String) -> Unit
) {
    var reason by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Причина отклонения") },
        text = {
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Укажите причину") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(reason) },
                enabled = reason.isNotBlank() // Cannot confirm with an empty reason
            ) {
                Text("Отклонить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}


@Preview(name = "Article Review Screen", showBackground = true)
@Composable
fun ArticleReviewScreenPreview() {
    MaterialTheme {
        ArticleReviewScreen(
            article = DummyData.articles.first { it.status == "ON_MODERATION" },
            onApprove = {},
            onReject = {},
            onNavigateUp = {}
        )
    }
}

@Preview(name = "Rejection Reason Dialog", showBackground = true)
@Composable
fun RejectReasonDialogPreview() {
    MaterialTheme {
        // We place it in a Box to simulate how it looks on top of a screen
        Box(Modifier.fillMaxSize()) {
            RejectReasonDialog(onDismiss = {}, onConfirm = {})
        }
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\presentation\moderator\ModeratorDashboardScreen.kt

```kt
package kz.petproject.gts_tz.ui.presentation.moderator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData

// --- Screen 1: Moderator's Dashboard ---

/**
 * The main screen for a moderator to see a list of articles awaiting review.
 *
 * @param articlesForModeration The list of articles with "ON_MODERATION" status.
 * @param onArticleClick Lambda triggered when a moderator clicks on an article to review it.
 * @param onNavigateUp Lambda for back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeratorDashboardScreen(
    articlesForModeration: List<Article>,
    onArticleClick: (articleId: Int) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("На модерации") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (articlesForModeration.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Все статьи проверены. Отличная работа!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(articlesForModeration, key = { it.id }) { article ->
                    Card(
                        onClick = { onArticleClick(article.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(article.title, style = MaterialTheme.typography.titleMedium)
                                Text("Автор: ${article.author.name}", style = MaterialTheme.typography.bodySmall)
                            }
                            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Проверить статью")
                        }
                    }
                }
            }
        }
    }
}


@Preview(name = "Moderator Dashboard", showBackground = true)
@Composable
fun ModeratorDashboardScreenPreview() {
    MaterialTheme {
        ModeratorDashboardScreen(
            articlesForModeration = DummyData.articles.filter { it.status == "ON_MODERATION" },
            onArticleClick = {},
            onNavigateUp = {}
        )
    }
}

@Preview(name = "Moderator Dashboard - Empty", showBackground = true)
@Composable
fun ModeratorDashboardScreenEmptyPreview() {
    MaterialTheme {
        ModeratorDashboardScreen(
            articlesForModeration = emptyList(),
            onArticleClick = {},
            onNavigateUp = {}
        )
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\presentation\NewsFeedScreen.kt

```kt
package kz.petproject.gts_tz.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.ui.model.ArticleCard

/**
 * The main news feed screen, accessible to all users.
 * @param articles The list of published articles to display.
 * @param onArticleClick Lambda triggered when a user clicks on an article card.
 * @param currentUserRole The role of the currently logged-in user (e.g., "AUTHOR"). Null if anonymous.
 * @param onFabClick Lambda for the FAB's click action.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen(
    articles: List<Article>,
    onArticleClick: (articleId: Int) -> Unit,
    currentUserRole: String? = null, // Can be null for a general user
    onFabClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Новости") })
        },
        // --- NEW FLOATING ACTION BUTTON LOGIC ---
        floatingActionButton = {
            // Only show the FAB if the current user is an Author
            if (currentUserRole == "AUTHOR") {
                FloatingActionButton(
                    onClick = onFabClick,
                    ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Создать новую статью"
                    )
                }
            }
        }
    ) { paddingValues ->
        if (articles.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Пока нет опубликованных новостей.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(articles, key = { it.id }) { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onArticleClick(article.id) }
                    )
                }
            }
        }
    }
}

// --- UPDATED & NEW PREVIEWS ---

@Preview(name = "News Feed (Public User)", showBackground = true)
@Composable
fun NewsFeedScreenPreview_Public() {
    MaterialTheme {
        val publishedArticles = DummyData.articles.filter { it.status == "PUBLISHED" }
        NewsFeedScreen(
            articles = publishedArticles,
            onArticleClick = {},
            currentUserRole = null // This user is not an author, so no FAB
        )
    }
}

// --- NEW PREVIEW ---
@Preview(name = "News Feed (As Author)", showBackground = true)
@Composable
fun NewsFeedScreenPreview_AsAuthor() {
    MaterialTheme {
        val publishedArticles = DummyData.articles.filter { it.status == "PUBLISHED" }
        NewsFeedScreen(
            articles = publishedArticles,
            onArticleClick = {},
            currentUserRole = "AUTHOR", // This user IS an author, so the FAB will appear
            onFabClick = {}
        )
    }
}

@Preview(name = "News Feed Empty State", showBackground = true)
@Composable
fun NewsFeedScreenPreview_Empty() {
    MaterialTheme {
        NewsFeedScreen(
            articles = emptyList(),
            onArticleClick = {}
        )
    }
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\theme\Color.kt

```kt
package kz.petproject.gts_tz.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
```

# app\src\main\java\kz\petproject\gts_tz\ui\theme\Theme.kt

```kt
package kz.petproject.gts_tz.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun GTS_TZTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

# app\src\main\java\kz\petproject\gts_tz\ui\theme\Type.kt

```kt
package kz.petproject.gts_tz.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
```

# app\src\main\res\drawable\ic_launcher_background.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#3DDC84"
        android:pathData="M0,0h108v108h-108z" />
    <path
        android:fillColor="#00000000"
        android:pathData="M9,0L9,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,0L19,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M29,0L29,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M39,0L39,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M49,0L49,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M59,0L59,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M69,0L69,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M79,0L79,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M89,0L89,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M99,0L99,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,9L108,9"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,19L108,19"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,29L108,29"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,39L108,39"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,49L108,49"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,59L108,59"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,69L108,69"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,79L108,79"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,89L108,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,99L108,99"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,29L89,29"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,39L89,39"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,49L89,49"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,59L89,59"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,69L89,69"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,79L89,79"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M29,19L29,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M39,19L39,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M49,19L49,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M59,19L59,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M69,19L69,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M79,19L79,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
</vector>

```

# app\src\main\res\drawable\ic_launcher_foreground.xml

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:aapt="http://schemas.android.com/aapt"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path android:pathData="M31,63.928c0,0 6.4,-11 12.1,-13.1c7.2,-2.6 26,-1.4 26,-1.4l38.1,38.1L107,108.928l-32,-1L31,63.928z">
        <aapt:attr name="android:fillColor">
            <gradient
                android:endX="85.84757"
                android:endY="92.4963"
                android:startX="42.9492"
                android:startY="49.59793"
                android:type="linear">
                <item
                    android:color="#44000000"
                    android:offset="0.0" />
                <item
                    android:color="#00000000"
                    android:offset="1.0" />
            </gradient>
        </aapt:attr>
    </path>
    <path
        android:fillColor="#FFFFFF"
        android:fillType="nonZero"
        android:pathData="M65.3,45.828l3.8,-6.6c0.2,-0.4 0.1,-0.9 -0.3,-1.1c-0.4,-0.2 -0.9,-0.1 -1.1,0.3l-3.9,6.7c-6.3,-2.8 -13.4,-2.8 -19.7,0l-3.9,-6.7c-0.2,-0.4 -0.7,-0.5 -1.1,-0.3C38.8,38.328 38.7,38.828 38.9,39.228l3.8,6.6C36.2,49.428 31.7,56.028 31,63.928h46C76.3,56.028 71.8,49.428 65.3,45.828zM43.4,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2c-0.3,-0.7 -0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C45.3,56.528 44.5,57.328 43.4,57.328L43.4,57.328zM64.6,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2s-0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C66.5,56.528 65.6,57.328 64.6,57.328L64.6,57.328z"
        android:strokeWidth="1"
        android:strokeColor="#00000000" />
</vector>
```

# app\src\main\res\mipmap-anydpi-v26\ic_launcher_round.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
    <monochrome android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
```

# app\src\main\res\mipmap-anydpi-v26\ic_launcher.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
    <monochrome android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
```

# app\src\main\res\mipmap-hdpi\ic_launcher_round.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-hdpi\ic_launcher.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-mdpi\ic_launcher_round.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-mdpi\ic_launcher.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-xhdpi\ic_launcher_round.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-xhdpi\ic_launcher.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-xxhdpi\ic_launcher_round.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-xxhdpi\ic_launcher.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-xxxhdpi\ic_launcher_round.webp

This is a binary file of the type: Image

# app\src\main\res\mipmap-xxxhdpi\ic_launcher.webp

This is a binary file of the type: Image

# app\src\main\res\values\colors.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="purple_200">#FFBB86FC</color>
    <color name="purple_500">#FF6200EE</color>
    <color name="purple_700">#FF3700B3</color>
    <color name="teal_200">#FF03DAC5</color>
    <color name="teal_700">#FF018786</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
</resources>
```

# app\src\main\res\values\strings.xml

```xml
<resources>
    <string name="app_name">GTS_TZ</string>
</resources>
```

# app\src\main\res\values\themes.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <style name="Theme.GTS_TZ" parent="android:Theme.Material.Light.NoActionBar" />
</resources>
```

# app\src\main\res\xml\backup_rules.xml

```xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample backup rules file; uncomment and customize as necessary.
   See https://developer.android.com/guide/topics/data/autobackup
   for details.
   Note: This file is ignored for devices older that API 31
   See https://developer.android.com/about/versions/12/backup-restore
-->
<full-backup-content>
    <!--
   <include domain="sharedpref" path="."/>
   <exclude domain="sharedpref" path="device.xml"/>
-->
</full-backup-content>
```

# app\src\main\res\xml\data_extraction_rules.xml

```xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample data extraction rules file; uncomment and customize as necessary.
   See https://developer.android.com/about/versions/12/backup-restore#xml-changes
   for details.
-->
<data-extraction-rules>
    <cloud-backup>
        <!-- TODO: Use <include> and <exclude> to control what is backed up.
        <include .../>
        <exclude .../>
        -->
    </cloud-backup>
    <!--
    <device-transfer>
        <include .../>
        <exclude .../>
    </device-transfer>
    -->
</data-extraction-rules>
```

# app\src\test\java\kz\petproject\gts_tz\ExampleUnitTest.kt

```kt
package kz.petproject.gts_tz

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
```

# build.gradle.kts

```kts
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```

# gradle.properties

```properties
# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. For more details, visit
# https://developer.android.com/r/tools/gradle-multi-project-decoupled-projects
# org.gradle.parallel=true
# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official
# Enables namespacing of each library's R class so that its R class includes only the
# resources declared in the library itself and none from the library's dependencies,
# thereby reducing the size of the R class for that library
android.nonTransitiveRClass=true
```

# gradle\libs.versions.toml

```toml
[versions]
agp = "8.7.3"
kotlin = "2.0.0"
coreKtx = "1.16.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
lifecycleRuntimeKtx = "2.9.0"
activityCompose = "1.10.1"
composeBom = "2024.04.01"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }


```

# gradle\wrapper\gradle-wrapper.jar

This is a binary file of the type: Binary

# gradle\wrapper\gradle-wrapper.properties

```properties
#Wed Jul 02 16:10:24 QYZT 2025
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.9-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists

```

# gradlew

```
#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar


# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" -a "$darwin" = "false" -a "$nonstop" = "false" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            MAX_FD="$MAX_FD_LIMIT"
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            warn "Could not set maximum file descriptor limit: $MAX_FD"
        fi
    else
        warn "Could not query maximum file descriptor limit: $MAX_FD_LIMIT"
    fi
fi

# For Darwin, add options to specify how the application appears in the dock
if $darwin; then
    GRADLE_OPTS="$GRADLE_OPTS \"-Xdock:name=$APP_NAME\" \"-Xdock:icon=$APP_HOME/media/gradle.icns\""
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "$cygwin" = "true" -o "$msys" = "true" ] ; then
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`

    JAVACMD=`cygpath --unix "$JAVACMD"`

    # We build the pattern for arguments to be converted via cygpath
    ROOTDIRSRAW=`find -L / -maxdepth 1 -mindepth 1 -type d 2>/dev/null`
    SEP=""
    for dir in $ROOTDIRSRAW ; do
        ROOTDIRS="$ROOTDIRS$SEP$dir"
        SEP="|"
    done
    OURCYGPATTERN="(^($ROOTDIRS))"
    # Add a user-defined pattern to the cygpath arguments
    if [ "$GRADLE_CYGPATTERN" != "" ] ; then
        OURCYGPATTERN="$OURCYGPATTERN|($GRADLE_CYGPATTERN)"
    fi
    # Now convert the arguments - kludge to limit ourselves to /bin/sh
    i=0
    for arg in "$@" ; do
        CHECK=`echo "$arg"|egrep -c "$OURCYGPATTERN" -`
        CHECK2=`echo "$arg"|egrep -c "^-"`                                 ### Determine if an option

        if [ $CHECK -ne 0 ] && [ $CHECK2 -eq 0 ] ; then                    ### Added a condition
            eval `echo args$i`=`cygpath --path --ignore --mixed "$arg"`
        else
            eval `echo args$i`="\"$arg\""
        fi
        i=`expr $i + 1`
    done
    case $i in
        0) set -- ;;
        1) set -- "$args0" ;;
        2) set -- "$args0" "$args1" ;;
        3) set -- "$args0" "$args1" "$args2" ;;
        4) set -- "$args0" "$args1" "$args2" "$args3" ;;
        5) set -- "$args0" "$args1" "$args2" "$args3" "$args4" ;;
        6) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" ;;
        7) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" ;;
        8) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" ;;
        9) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" "$args8" ;;
    esac
fi

# Escape application args
save () {
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done
    echo " "
}
APP_ARGS=`save "$@"`

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "\"-Dorg.gradle.appname=$APP_BASE_NAME\"" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$APP_ARGS"

exec "$JAVACMD" "$@"

```

# gradlew.bat

```bat
@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar


@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%GRADLE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

```

# local.properties

```properties
## This file must *NOT* be checked into Version Control Systems,
# as it contains information specific to your local configuration.
#
# Location of the SDK. This is only used by Gradle.
# For customization when using a Version Control System, please read the
# header note.
#Wed Jul 02 16:15:17 QYZT 2025
sdk.dir=C\:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk

```

# settings.gradle.kts

```kts
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GTS_TZ"
include(":app")
 
```

