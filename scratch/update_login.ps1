$filePath = "c:\tphu\PL_TRACUU\tracuu\src\main\resources\templates\login.html"
$content = Get-Content -Path $filePath -Raw -Encoding UTF8
$oldText = '<button class="mt-stack-sm w-full bg-primary text-on-primary font-label-sm text-label-sm py-3 rounded hover:opacity-90 transition-opacity flex justify-center items-center gap-2" type="submit">
                    <span class="material-symbols-outlined text-sm">login</span>
                    Tra cứu
                </button>'
$newText = '<button id="loginBtn" class="mt-stack-sm w-full bg-primary text-on-primary font-label-sm text-label-sm py-3 rounded hover:opacity-90 transition-opacity flex justify-center items-center gap-2" type="submit">
                    <span class="material-symbols-outlined text-sm">login</span>
                    <span id="btnText">Tra cứu</span>
                </button>'
$content = $content.Replace($oldText, $newText)

$oldScript = 'document.querySelector(''form'').addEventListener(''submit'', function(e) {
            console.log(''Form is being submitted...'');
            // alert(''?ang g-i yAu c u `ng nh-p...'');
        });'
$newScript = 'document.querySelector(''form'').addEventListener(''submit'', function(e) {
            const btn = document.getElementById(''loginBtn'');
            const btnText = document.getElementById(''btnText'');
            btn.disabled = true;
            btn.style.opacity = "0.7";
            btnText.innerText = "Đang kiểm tra...";
        });'
$content = $content.Replace($oldScript, $newScript)

Set-Content -Path $filePath -Value $content -Encoding UTF8
