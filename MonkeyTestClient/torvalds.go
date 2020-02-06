package main

var quotes = []string{`Standards are paper. I use paper to wipe my butt every day. That's how much that paper is worth.`,
`Every time I see some piece of medical research saying that caffeine is good for you, I high-five myself. Because I'm going to live forever.`,
`Toto, I don't think we're talking white-socks-and-sandals any more.`,
`Why don't we write code that just works? Or absent a "just works" set of patches, why don't we revert to code that has years of testing? This kind of "I broke things, so now I will jiggle things randomly until they unbreak" is not acceptable. [...] Don't just make random changes. There really are only two acceptable models of development: "think and analyze" or "years and years of testing on thousands of machines". Those two really do work.`,
`We're not masturbating around with some research project. We never were. Even when Linux was young, the whole and only point was to make a *usable* system. It's why it's not some crazy drug-induced microkernel or other random crazy thing.`,
`Somebody is trying to kill all the kernel developers. First we had two earthquakes - fine, this week God not only hates republicans, but apparently us kernel developers too. But we kernel developers laugh in the face of danger, and a 5.5 earthquake just makes us whimper and hide in the closet for a while.  But after we've stopped cowering in the closet, there's a knock on the door, and the conference organizers are handing out skate boards, with the innocent explanations of "We're in San Diego, after all". If that's not a sign of somebody trying to kill us, I don't know what is. Handing out skate boards to a bunch of geeks sounds like a seriously misguided thing to do.`,
`[…] I really hate big laptops. I can't understand people who lug around 15" (or 17"!) monsters. The right weight for a laptop is 1kg, no more.`,
`Obsessing about things is important, and things really do matter, but if you can't let go of them, you'll end up crazy.`,
`WE DO NOT BREAK USERSPACE!`,
`I'm not sentimental. Good riddance.`,
`Of course, I'd also suggest that whoever was the genius who thought it was a good idea to read things ONE F*CKING BYTE AT A TIME with system calls for each byte should be retroactively aborted. Who the f*ck does idiotic things like that? How did they not die as babies, considering that they were likely too stupid to find a tit to suck on?`,
`People say that you should not micro-optimize. But if what you love is micro-optimization... that's what you should do.`,
`I like offending people, because I think people who get offended should be offended.`,
`Nvidia has been the single worst company we've ever dealt with. So Nvidia... fuck you!`,
`I wish everybody was as nice as I am.`,
`I started Linux as a desktop operating system. And it's the only area where Linux hasn't completely taken over. That just annoys the hell out of me.`,
`I realize that lawyers are brought up (probably from small children) to think that "technically true" is what matters, but when you make public PR statements, they should be more than "technically" true. They should be honest. There's a big f*cking difference.`,
`Microsoft isn't evil, they just make really crappy operating systems.`,
`But this is definitely another of those "This is our most desperate hour. Help me, Al-biwan Ke-Viro, you're my only hope" issues. Al? Please don't make me wear that golden bikini.`,
`I hope I won't end up having to hunt you all down and kill you in your sleep.`,
`Whoever came up with "hold the shift key for eight seconds to turn on 'your keyboard is buggered' mode" should be shot.`,
`There aren't enough swear-words in the English language, so now I'll have to call you perkeleen vittupää just to express my disgust and frustration with this crap.`,
`That's the spirit. Greg has taught you well. You have controlled your fear. Now, release your anger. Only your hatred can destroy me. Come to the dark side, Sarah. We have cookies.`,
`Because if you want me to "act professional", I can tell you that I'm not interested. I'm sitting in my home office wearing a bathrobe. The same way I'm not going to start wearing ties, I'm *also* not going to buy into the fake politeness, the lying, the office politics and backstabbing, the passive aggressiveness, and the buzzwords. Because THAT is what "acting professionally" results in: people resort to all kinds of really nasty things because they are forced to act out their normal urges in unnatural ways.`,
`XML is crap. Really. There are no excuses. XML is nasty to parse for humans, and it's a disaster to parse even for computers. There's just no reason for that horrible crap to exist.`,
`Lookie here, your compiler does some absolutely insane things with the spilling, including spilling a *constant*. For chrissake, that compiler shouldn't have been allowed to graduate from kindergarten. We're talking "sloth that was dropped on the head as a baby" level retardation levels here.`,
`I don't respect people unless I think they deserve the respect. There are people who think that respect is something that should be given, and I happen to be one of the people who is perfectly happy saying no; respect should be earned. And without being earned, you don't get it. It's really that simple.`,
`One of the things, none of the distributions have ever done right is application packaging [...] making binaries for linux desktop applications is a major fucking pain in the ass.`,
`[GPL] version 3 was not a good "here we give you version 2" and then we try to sneak in this new rules and try force everyone to upgrade; that was the part I disliked. The FSF did really sneaky stuff, downright immoral in my opinion.`,
`I may be a huge computer nerd, but even so I don't think education should be about computers. Not as a subject, and not as a classroom resource either.`,
`On the internet nobody can hear you being subtle.`,
`I don’t care about you.`,
`"the most important part of open source is that people are allowed to do what they are good at" and "all that [diversity] stuff is just details and not really important."`,
`I am a lazy person, which is why I like open source, for other people to do work for me.`,
`Christ, people. Learn C, instead of just stringing random characters together until it compiles (with warnings).`,
`Get rid of it. And I don't *ever* want to see that shit again.`,
`We don't merge kernel code just because user space was written by a retarded monkey on crack.`,
`I've actually felt slightly uncomfortable at TED for the last two days, because there's a lot of vision going on, right? And I am not a visionary. I do not have a five-year plan. I'm an engineer. And I think it's really -- I mean -- I'm perfectly happy with all the people who are walking around and just staring at the clouds and looking at the stars and saying, "I want to go there." But I'm looking at the ground, and I want to fix the pothole that's right in front of me before I fall in. This is the kind of person I am.`,
`I was 21 at the time, so I was young, but I had already programmed for half my life, basically. And every project before that had been completely personal and it was a revelation when people just started commenting, started giving feedback on your code. And even before they started giving code back, that was, I think, one of the big moments where I said, "I love other people!" Don't get me wrong -- I'm actually not a people person.`,
`The desktop hasn't really taken over the world like Linux has in many other areas, but just looking at my own use, my desktop looks so much better than I ever could have imagined. Despite the fact that I'm known for sometimes not being very polite to some of the desktop UI people, because I want to get my work done. Pretty is not my primary thing. I actually am very happy with the Linux desktop, and I started the project for my own needs, and my needs are very much fulfilled. That's why, to me, it's not a failure. I would obviously love for Linux to take over that world too, but it turns out it's a really hard area to enter. I'm still working on it. It's been 25 years. I can do this for another 25. I'll wear them down.`,
`Lawsuits destroy community. They destroy trust. They would destroy all the goodwill we've built up over the years by being nice.[5]`,
`The fact is, the people who have created open source and made it a success have been the developers doing work - and the companies that we could get involved by showing that we are not all insane crazy people like the FSF. The people who have *destroyed* projects have been lawyers that claimed to be out to "save" those projects.[6]`,
`None of this "there is no way to continue" bullshit. Because it is pure and utter SHIT.`,
`BULLSHIT. Have you _looked_ at the patches you are talking about? You should have - several of them bear your name. [...] As it is, the patches are COMPLETE AND UTTER GARBAGE. [...] WHAT THE F*CK IS GOING ON?`,
`It looks like the IT security world has hit a new low. If you work in security, and think you have some morals, I think you might want to add the tag-line "No, really, I'm not a whore. Pinky promise" to your business card. Because I thought the whole industry was corrupt before, but it's getting ridiculous. At what point will security people admit they have an attention-whoring problem?`,
`If 386BSD had been available when I started on Linux, Linux would probably never had happened.`,
`Software is like sex; it's better when it's free.`,
`The memory management on the PowerPC can be used to frighten small children.`,
`OK, I admit it. I was just a front-man for the real fathers of Linux, the Tooth Fairy and Santa Claus.`,
`95 percent of all software developers believe they are in the top 5 percent when it comes to knowledge and skills [citation needed].`,
`Guess what? Wheels have been round for a really long time, and anybody who "reinvents" the new wheel is generally considered a crackpot. It turns out that "round" is simply a good form for a wheel to have. It may be boring, but it just tends to roll better than a square, and "hipness" has nothing what-so-ever to do with it.`,
`I don't doubt at all that virtualization is useful in some areas. What I doubt rather strongly is that it will ever have the kind of impact that the people involved in virtualization want it to have.`,
`Now, most of you are probably going to be totally bored out of your minds on Christmas day, and here's the perfect distraction. Test 2.6.15-rc7. All the stores will be closed, and there's really nothing better to do in between meals.`,
`First off, I'm actually perfectly well off. I live in a good-sized house, with a nice yard, with deer occasionally showing up and eating the roses (my wife likes the roses more, I like the deer more, so we don't really mind). I've got three kids, and I know I can pay for their education. What more do I need? The thing is, being a good programmer actually pays pretty well; being acknowledged as being world-class pays even better. I simply didn't need to start a commercial company. And it's just about the least interesting thing I can even imagine. I absolutely hate paperwork. I couldn't take care of employees if I tried. A company that I started would never have succeeded – it's simply not what I'm interested in! So instead, I have a very good life, doing something that I think is really interesting, and something that I think actually matters for people, not just me. And that makes me feel good.`,
`So LSM stays in. No ifs, buts, maybes or anything else. When I see the security people making sane arguments and agreeing on something, that will change. Quite frankly, I expect hell to freeze over before that happens, and pigs will be nesting in trees. But hey, I can hope.`,
`So I would not be surprised if the globbing libraries, for example, will do NFD-mangling in order to glob "correctly", so even programs ported from real Unix might end up getting pathnames subtly changed into NFD as part of some hot library-on-library action with UTF hackery inside.`}
