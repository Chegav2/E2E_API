param(
    [ValidateSet('all', 'ui', 'api')]
    [string]$Suite = 'all',
    [string]$Tag,
    [switch]$Headless
)

$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$jdkDir = Get-ChildItem -Path (Join-Path $repoRoot 'tools') -Directory |
    Where-Object { $_.Name -like 'jdk-17*' } |
    Select-Object -First 1
$mavenDir = Get-ChildItem -Path (Join-Path $repoRoot 'tools') -Directory |
    Where-Object { $_.Name -like 'apache-maven-*' } |
    Select-Object -First 1

if (-not $jdkDir -or -not $mavenDir) {
    throw 'No se encontraron el JDK o Maven portables dentro de tools.'
}

$env:JAVA_HOME = $jdkDir.FullName
$env:Path = "$($jdkDir.FullName)\bin;$($mavenDir.FullName)\bin;$env:Path"

$mavenArgs = @()
if ($Suite -eq 'api') {
    $mavenArgs += 'test'
} else {
    $mavenArgs += 'clean'
    $mavenArgs += 'verify'
}
if ($Suite -eq 'ui') {
    $mavenArgs += '-Dtest=PurchaseE2ERunner'
    if ($Headless) { $mavenArgs += '-Dheadless=true' }
}
if ($Suite -eq 'api') {
    $mavenArgs += '-Dtest=KarateApiRunner'
}
if ($Tag) {
    if ($Suite -eq 'api') {
        $mavenArgs += "-Dkarate.filter.tags=$Tag"
    } else {
        $mavenArgs += "-Dcucumber.filter.tags=$Tag"
    }
}

& (Join-Path $mavenDir.FullName 'bin\mvn.cmd') @mavenArgs
exit $LASTEXITCODE
