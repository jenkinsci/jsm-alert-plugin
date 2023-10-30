
# Jira Service Management Integration Jenkins Plugin

[![Atlassian license](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat-square)](LICENSE) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](CONTRIBUTING.md)

Use JSM Jenkins Integration to forward Jenkins build alerts to Jira Service Management. JSM determines the right people to notify based on on-call schedules– notifies via email, text messages (SMS), phone calls, and iOS & Android push notifications, and escalates alerts until the alert is acknowledged or closed.

## Installation and Tests

[Install Maven](https://github.com/jenkinsci/workflow-plugin) and JDK.
```
$ mvn -version | grep -v home
Apache Maven 3.5.0 (; 2017-04-03T22:39:06+03:00)
Java version: 1.8.0_131, vendor: Oracle Corporation
Default locale: en_US, platform encoding: UTF-8
```
Create an HPI file to install in Jenkins (HPI file will be in
`target/jsm-integration.hpi`).

    mvn hpi:hpi

## Contributions

Contributions to Jira Service Management Integration Jenkins Plugin are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details.

## License

Copyright (c) [2023] Atlassian US., Inc.
Apache 2.0 licensed, see [LICENSE](LICENSE) file.

<br/>

[![With ❤️ from Atlassian](https://raw.githubusercontent.com/atlassian-internal/oss-assets/master/banner-cheers.png)](https://www.atlassian.com)
