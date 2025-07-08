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
        User("1", "Admin User", "admin", "ADMIN"),
        User("2", "Иван Петров", "ivan_p", "AUTHOR"),
        User("3", "Мария Сидорова", "maria_s", "AUTHOR"),
        User("4", "Сергей Васильев", "sergey_v", "MODERATOR")
    )
    // A list of sample articles covering all possible statuses.
    val articles = listOf(
        Article(
            id = "1",
            title = "Compose — будущее Android-разработки",
            content = "Jetpack Compose — это современный декларативный набор инструментов для создания нативного пользовательского интерфейса Android. Он упрощает и ускоряет разработку пользовательского интерфейса на Android, позволяя быстро воплощать приложения в жизнь с меньшим количеством кода.",
            author = authors[0],
            status = "PUBLISHED",
            publishedAt = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1) // Published yesterday
        ),
        Article(
            id = "6",
            title = "Идеи для новой статьи",
            content = "Начать с обзора последних...", // Can be empty or partially filled
            author = authors[0], // Belongs to "Иван Петров"
            status = "DRAFT" // The new status
        ),
        Article(
            id = "2",
            title = "Что нового в Kotlin 1.9?",
            content = "В новой версии Kotlin были представлены значительные улучшения производительности компилятора, новые возможности языка и обновление стандартной библиотеки. Рассмотрим ключевые изменения...",
            author = authors[1],
            status = "PUBLISHED",
            publishedAt = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3) // Published 3 days ago
        ),
        Article(
            id = "3",
            title = "KMM для кросс-платформенной разработки",
            content = "Kotlin Multiplatform Mobile (KMM) позволяет использовать общую кодовую базу для бизнес-логики приложений iOS и Android. Это помогает сэкономить время и обеспечить консистентность.",
            author = authors[0],
            status = "ON_MODERATION"
        ),
        Article(
            id = "4",
            title = "Архитектура MVVM",
            content = "MVVM (Model-View-ViewModel) - это...",
            author = authors[1],
            status = "REJECTED",
            rejectionReason = "Статья слишком короткая. Пожалуйста, раскройте тему более подробно."
        ),
        Article(
            id = "5",
            title = "Знакомство с Coroutines",
            content = "Асинхронное программирование — неотъемлемая часть современных приложений. В Kotlin для этих целей существуют корутины, которые позволяют писать асинхронный код последовательно и понятно.",
            author = authors[0],
            status = "ON_MODERATION"
        )
    )

}