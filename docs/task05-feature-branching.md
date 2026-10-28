# Task 05: Feature Development with Branching

## Objective
To implement a feature on a feature branch using add, commit, push, pull and log, raise a pull request, review the changes and merge into the development branch.

## 1. Create the feature branch

```
git checkout develop
git pull origin develop
git checkout -b feature/health-endpoint
```

## 2. Implement the feature (/health endpoint)

```
@RestController
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
```

```
$ curl -i http://localhost:8081/health
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8
Content-Length: 2

OK
```

## 3. Add, commit and push

```
git add .
git commit -m "feat: add /health endpoint for status checks"
git push -u origin feature/health-endpoint
```

## 4. Pull request

> Screenshot: Figure: Pull request #1 from feature/health-endpoint into develop (see Reports/Task_05 PDF)

## 5. Review comment

> Screenshot: Figure: Review comment on HealthController.java (see Reports/Task_05 PDF)

## 6. Merge and evidence

> Screenshot: Figure: Merge into develop and git log --graph showing the merge commit 29d1db3 (see Reports/Task_05 PDF)

## Conclusion
A feature was developed on its own branch, raised as pull request #1, reviewed with a comment and merged into develop, showing the complete branching workflow.
