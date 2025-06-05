# --- AndroidX ViewModel ---
-keep public class * extends androidx.lifecycle.ViewModel

# --- Moshi JSON (если используешь) ---
-keepclassmembers class * {
    @com.squareup.moshi.* <fields>;
}
-dontwarn com.squareup.moshi.**

# --- Retrofit (интерфейсы и модели) ---
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# --- OkHttp ---
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

# --- ExoPlayer (если используется) ---
-dontwarn com.google.android.exoplayer2.**
-keep class com.google.android.exoplayer2.** { *; }

# --- Dagger 2 ---
-keep class dagger.** { *; }
-dontwarn dagger.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**

# --- Firebase ---
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# --- Navigation SafeArgs ---
-keep class **NavDirections
-keepclassmembers class * {
    public static *** from*(...);
    public static *** action*();
}

# --- Основные правила ---
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }

# --- Сохраняем MainActivity и все фрагменты ---
-keep class com.example.everynoiseatonce.presentation.ui.** { *; }

# --- Удаляем неиспользуемые ресурсы ---
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}
