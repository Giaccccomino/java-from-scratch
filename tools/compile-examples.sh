#!/usr/bin/env bash
# Compiles every chapter example, one by one, and checks that the ones in the
# "intentional-errors" folders do NOT compile (they contain errors on purpose).
# The examples that use Jackson (Chapter 41) are compiled by Maven: ./mvnw verify.
set -u
cd "$(dirname "$0")/../chapters"
tmp=$(mktemp -d)
ok=0; ko=0; skipped=0
for dir in chapter-*/; do
  for f in "$dir"*.java; do
    [ -e "$f" ] || continue
    if grep -q "com.fasterxml" "$f"; then skipped=$((skipped+1)); continue; fi
    if javac -encoding UTF-8 -sourcepath "$dir" -d "$tmp/out" "$f" 2>"$tmp/err"; then
      ok=$((ok+1))
    else
      ko=$((ko+1)); echo "ERROR in $f"; cat "$tmp/err"
    fi
  done
  for f in "$dir"intentional-errors/*.java; do
    [ -e "$f" ] || continue
    if javac -encoding UTF-8 -sourcepath "${dir}src" -d "$tmp/out" "$f" 2>/dev/null; then
      ko=$((ko+1)); echo "WARNING: $f should contain an error, but it compiles"
    else
      ok=$((ok+1))
    fi
  done
done
# Chapter 32: packages and modules
javac -encoding UTF-8 -d "$tmp/p" $(find chapter-32/src -name "*.java") && ok=$((ok+1)) || { ko=$((ko+1)); echo "ERROR chapter-32/src"; }
javac -encoding UTF-8 -d "$tmp/m" --module-source-path chapter-32/m -m aurora.app && ok=$((ok+1)) || { ko=$((ko+1)); echo "ERROR chapter-32/m"; }
# The solutions of Appendix D
javac -encoding UTF-8 -d "$tmp/s" ../solutions/*.java && ok=$((ok+1)) || { ko=$((ko+1)); echo "ERROR solutions"; }
rm -rf "$tmp"
echo "$ok checks passed, $ko failed, $skipped files with external dependencies (compiled by Maven)"
[ "$ko" -eq 0 ]
