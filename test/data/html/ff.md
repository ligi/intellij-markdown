Changes since 0.10.740 (20.11.2015)

This release focuses mostly on fixes and improvements.

To install the plugin, visit the [readme](https://github.com/go-lang-plugin-org/go-lang-idea-plugin#pre-release-builds).

# Changes

## General

IDEA 16 EAP support is introduced
Go 1.6 SDK is supported

## Plan9

Thanks to @stuartcarnie now we have a syntax highlighting for Plan9 files

<img width="537" alt="plan9" src="https://cloud.githubusercontent.com/assets/140920/12535279/eefac0ea-c28e-11e5-9706-f21b33baefbe.png">

## Inspections & QuickFixes

- Report a warning for exported variables and constants in multi-spec declarations and suggest to extract it

![exported_variable_should_have_own_declaration](https://cloud.githubusercontent.com/assets/140920/12535333/382d169a-c290-11e5-8593-535a048f8fc2.gif)

- Suggest to delete unused constants and variables

![delete_unused_variable_const](https://cloud.githubusercontent.com/assets/140920/12535317/c161c5ce-c28f-11e5-81ea-7a7911b37d6d.gif)

- Report a error on missing key in map literal

<img width="374" alt="screenshot 2016-01-24 11 59 16" src="https://cloud.githubusercontent.com/assets/140920/12535378/fe1e41a2-c291-11e5-829b-d1a53de54680.png">
- Report a error on not an expression function argument

<img width="305" alt="screenshot 2016-01-24 12 01 38" src="https://cloud.githubusercontent.com/assets/140920/12535381/4a56ff00-c292-11e5-84cc-518419a0aef4.png">

- Report a error on types used as expressions

<img width="264" alt="screenshot 2016-01-24 12 19 58" src="https://cloud.githubusercontent.com/assets/140920/12535442/cc5a1364-c294-11e5-9711-b96f02fe4ebc.png">

- Report a error about missing argument conversion

<img width="432" alt="screenshot 2016-01-24 12 20 36" src="https://cloud.githubusercontent.com/assets/140920/12535447/eb0a53fa-c294-11e5-9158-8bb8c0d853fc.png">

- Highlight unused constants

<img width="245" alt="screenshot 2016-01-24 12 22 34" src="https://cloud.githubusercontent.com/assets/140920/12535458/312ce3fc-c295-11e5-8f5d-3da5f617048d.png">


## Other
- Less polluted completion list: `iota` only inside constant declarations, deleted self-assignments and some keywords
- Add more foldings for `if/else/for/switch/select` statements
- Show constant value on Cltr+Hover

<img width="251" alt="screenshot 2016-01-24 12 05 56" src="https://cloud.githubusercontent.com/assets/140920/12535399/ed7fbb04-c292-11e5-9d50-3dce0c5b3966.png">

- Various fixes of resolving, types inferring, quick documentation feature, formatting

- Proper completion and resolving for break statements

<img width="654" alt="proper_break_labels_resolve_and_completion" src="https://cloud.githubusercontent.com/assets/140920/12535285/06df5400-c28f-11e5-91f6-c0c9ca294c6b.png">

For a list of all changes in the plugin, you can visit [this page](https://github.com/go-lang-plugin-org/go-lang-idea-plugin/compare/273df35...02c39130d).

Develop with pleasure!
