twittercli
==========

A command line power-tool for Twitter based on https://github.com/sferik/t but in pure Java.

The reason for porting or migrating the project was to remove the dependency on Ruby, and second
add more commands that I find useful in every day work.

Changelog
---------

**Current Development**

* Initial release

Commands Supported
------------------

```
$ t authorize
$ t update "status"
$ t followers
$ t whoami
$ t version
```

Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility,
`twittercli` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
-------

```
twittercli - Command line power tool for Twitter
Copyright (c) 2014, Sandeep Gupta

http://sangupta.com/projects/twittercli

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
