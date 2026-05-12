$filePath = "c:\tphu\PL_TRACUU\tracuu\src\main\resources\templates\fragments.html"
$content = Get-Content -Path $filePath -Raw -Encoding UTF8
$oldText = '<div sec:authorize="isAuthenticated()">
            <form th:action="@{/logout}" method="post" class="inline">
                <button type="submit" class="text-on-surface-variant hover:text-error transition-colors font-label-sm text-label-sm px-4 py-2 rounded border border-outline-variant">Đăng xuất</button>
            </form>
        </div>'
# Note: The text in the file might have different encoding or characters. 
# I will use a regex to match the structure instead.

$pattern = '(?s)<div sec:authorize="isAuthenticated\(\)">.*?<button type="submit".*?>.*?</button>.*?/form>.*?/div>'
$replacement = '<div sec:authorize="isAuthenticated()" class="flex items-center gap-3">
            <div class="flex flex-col items-end">
                <span class="font-label-sm text-label-sm text-primary font-bold" th:text="${#authentication.name}">User</span>
                <span class="text-[10px] text-on-surface-variant uppercase tracking-wider">Thí sinh</span>
            </div>
            <form th:action="@{/logout}" method="post" class="inline">
                <button type="submit" class="text-on-surface-variant hover:text-error transition-colors font-label-sm text-label-sm px-4 py-2 rounded border border-outline-variant flex items-center gap-1">
                    <span class="material-symbols-outlined text-sm">logout</span>
                    Đăng xuất
                </button>
            </form>
        </div>'

$content = [regex]::Replace($content, $pattern, $replacement)
Set-Content -Path $filePath -Value $content -Encoding UTF8
