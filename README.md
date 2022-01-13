The YouView service was created to help a fellow student with his project. The aim of his project was to analyze user behavior when accessing YouTube on mobile devices. Data gathered will be used to construct models of user behavior which can aid optimization of applications and quality monitoring systems.

Some of the basic tracked events are (more were added later):
- pause
- play
- full screen on/off
- resolution change
- skip ads
- visit advertiser
- accessibility turned on/off
- data saver
- play next
- play previous
- rewind
- fast forward
- replay
- end of video

Supports parsing events in multiple languages if a language xml resource for the default locale is provided.\
Overriding default ```isMatchOrNull``` method on event allows for fine grained matching.