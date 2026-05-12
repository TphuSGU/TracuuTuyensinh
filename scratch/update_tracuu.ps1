$filePath = "c:\tphu\PL_TRACUU\tracuu\src\main\resources\templates\tracuu.html"
$content = Get-Content -Path $filePath -Raw -Encoding UTF8
$oldText = '<main class="flex-grow w-full max-w-container-max mx-auto px-gutter py-stack-lg flex flex-col gap-stack-lg mt-stack-md">
    <div class="grid grid-cols-1 lg:grid-cols-12 gap-stack-lg">'
$newText = '<main class="flex-grow w-full max-w-container-max mx-auto px-gutter py-stack-lg flex flex-col gap-stack-lg mt-stack-md">
    <!-- Success Message Alert -->
    <div th:if="${successMessage != null}" class="bg-success-container border border-success-container rounded-lg p-stack-sm flex items-center gap-stack-sm mb-stack-sm animate-pulse">
        <span class="material-symbols-outlined text-on-success-container" style="font-variation-settings: ''FILL'' 1;">check_circle</span>
        <p class="font-body-md text-on-success-container" th:text="${successMessage}">Đăng nhập thành công!</p>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-12 gap-stack-lg">'
$content = $content.Replace($oldText, $newText)
Set-Content -Path $filePath -Value $content -Encoding UTF8
