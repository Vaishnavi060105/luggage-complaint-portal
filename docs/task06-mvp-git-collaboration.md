# Task 06: MVP Completion and Git Collaboration

## Objective
To complete the MVP functions, and to demonstrate a second feature branch, merge-conflict creation and resolution, tagging and a release-ready source baseline.

## 1. Two feature branches changing the same line

> Screenshot: Figure: Branches feature/readme-update-a and feature/readme-update-b (see Reports/Task_06 PDF)

## 2. Merge conflict

> Screenshot: Figure: CONFLICT (content): Merge conflict in README.md (see Reports/Task_06 PDF)

## 3. Conflict resolved

The conflict markers were removed in README.md, one title was kept, and the merge was committed and pushed.

```
*   53b018f (HEAD -> develop, origin/develop) merge: resolve README conflict between A and B
|\
| * 9e8b295 (feature/readme-update-a) docs: update README title (A)
* | 45cb204 (feature/readme-update-b) docs: update README title (B)
|/
*   29d1db3 (main) Merge pull request #1 from Vaishnavi060105/feature/health-endpoint
```

## 4. Tagged release baseline

```
git tag -a v1.0.0 -m "MVP release v1.0.0"
git push origin v1.0.0

$ git tag
v1.0.0
53b018f (HEAD -> develop, tag: v1.0.0, origin/main, origin/develop, origin/HEAD, main) merge: resolve README conflict between A and B
```

## 5. Functional MVP

> Screenshot: Figure: Track a complaint: reference LCP-2026-000017 with status SUBMITTED and history (see Reports/Task_06 PDF)

> Evidence to add: Reviewer approving a complaint, the v1.0.0 tag on GitHub and the updated backlog

## Conclusion
A second pair of feature branches produced a merge conflict that was resolved, the source baseline was tagged v1.0.0, and the MVP functions were demonstrated.
