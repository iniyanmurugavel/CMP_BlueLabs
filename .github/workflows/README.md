# GitHub Workflows

## 🗺️ Generate Sitemap Workflow

### Overview
The `generate-sitemap.yml` workflow automatically generates a `sitemap.xml` file with:
- **Static pages**: Home, Portfolio, Search, Privacy Policy
- **Dynamic blog pages**: Fetched from Firebase Firestore in real-time

### How It Works

1. **Fetches blog data** from Firebase using the Firestore REST API
2. **Filters published blogs** (only includes `isPublished: true`)
3. **Uses custom URL slugs** from `url_str` field or generates from title
4. **Includes last modified dates** from `posted` timestamp
5. **Generates sitemap.xml** in `composeApp/src/wasmJsMain/resources/`
6. **Creates a Pull Request** with the updated sitemap

### Trigger Options

#### Manual Trigger
1. Go to **Actions** tab in GitHub
2. Select **Generate Sitemap** workflow
3. Click **Run workflow**
4. Wait for completion

#### Automatic Schedule
- Runs daily at midnight UTC
- Keeps sitemap fresh with new blog posts

### Required Secrets

Configure these in **Settings > Secrets and variables > Actions**:

| Secret Name | Description | Example |
|-------------|-------------|---------|
| `BASE_URL` | Your website's base URL | `https://bluelabs.in` |
| `FIREBASE_BASE_URL` | Firestore API endpoint | `https://firestore.googleapis.com/v1/projects/...` |
| `FIREBASE_BEARER` | Firebase API key | `AIzaSy...` |
| `GH_TOKEN` | GitHub token (optional) | `ghp_...` |
| `GITHUB_BASE_URL` | GitHub base URL (optional) | `https://api.github.com` |
| `SHA_SECRET_KEY` | SHA secret key (optional) | Your secret key |

### Local Testing

You can test sitemap generation locally:

```bash
# Make sure local.properties is configured
./gradlew :buildSrc:build
./gradlew :composeApp:generateSitemap
```

Check the generated file at:
```
composeApp/src/wasmJsMain/resources/sitemap.xml
```

### Output Example

```xml
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
  <url>
    <loc>https://bluelabs.in</loc>
    <lastmod>2025-10-08</lastmod>
    <changefreq>weekly</changefreq>
    <priority>1.0</priority>
  </url>
  <url>
    <loc>https://bluelabs.in/blog/kotlin-multiplatform-guide</loc>
    <lastmod>2025-10-05</lastmod>
    <changefreq>monthly</changefreq>
    <priority>0.8</priority>
  </url>
  <!-- More URLs... -->
</urlset>
```

### Workflow Steps

1. ✅ Checkout code
2. ✅ Setup JDK 17
3. ✅ Generate `local.properties` from secrets
4. ✅ Build `buildSrc` (compile sitemap generator)
5. ✅ Generate sitemap with dynamic blog data
6. ✅ Verify sitemap was created
7. ✅ Upload sitemap as artifact
8. ✅ Create Pull Request with changes

### Troubleshooting

**Problem:** Sitemap generation fails with "Failed to fetch blogs"
- **Solution:** Check `FIREBASE_BASE_URL` and `FIREBASE_BEARER` secrets are correct

**Problem:** Sitemap only contains static pages
- **Solution:** Verify blog data is published (`isPublished: true` in Firestore)

**Problem:** Blog URLs are incorrect
- **Solution:** Check `url_str` field in Firestore blog documents

**Problem:** Workflow doesn't trigger
- **Solution:** Ensure workflow file is in `.github/workflows/` directory on main branch

### Files Involved

- `.github/workflows/generate-sitemap.yml` - GitHub workflow definition
- `buildSrc/src/main/kotlin/SitemapGeneratorScript.kt` - Sitemap generator logic
- `buildSrc/build.gradle.kts` - Build configuration for generator
- `composeApp/build.gradle.kts` - Gradle task registration
- `composeApp/src/wasmJsMain/resources/sitemap.xml` - Generated output

### SEO Benefits

✅ Helps search engines discover all pages
✅ Updates automatically with new blog posts
✅ Includes accurate last modified dates
✅ Prioritizes important pages
✅ Improves indexing speed
