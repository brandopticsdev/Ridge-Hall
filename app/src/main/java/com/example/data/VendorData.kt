package com.example.data

data class MenuItem(
    val name: String,
    val description: String,
    val price: String
)

data class MenuCategory(
    val categoryName: String,
    val items: List<MenuItem>
)

data class Vendor(
    val id: String,
    val name: String,
    val tagline: String,
    val description: String,
    val category: String, // Brews, Seafood, Street Food, Comfort
    val logoUrl: String,
    val imageUrl: String,
    val hoursAlert: String, // "NOW OPEN" or "OPENING SOON"
    val isFeatured: Boolean = false,
    val operatingHours: List<Pair<String, String>> = listOf(
        "MON - THU" to "11AM - 9PM",
        "FRI - SAT" to "11AM - 10PM",
        "SUN" to "11AM - 8PM"
    ),
    val location: String = "Stall #04 - Ridge Hall\n15 S Ridge Ave, Ambler, PA",
    val menuCategories: List<MenuCategory> = emptyList()
)

object VendorData {
    val allVendors = listOf(
        Vendor(
            id = "twisted-gingers",
            name = "TWISTED GINGERS BREWING CO",
            tagline = "Celebrate life with a twist.",
            description = "Hand-crafted small-batch brews forged with local ingredients. A true foundry original.",
            category = "Brews",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLtk0KZ_GgwY9ituiIRlseOIpmPahbgz4s8oesowAQqVI7cI0OtNPzlZkjTusa4B3BNmAl2v-yicki9cgjw--mwORLDDhZ256eZYyp0y69BPy1wFA-3hsdLkr_fpMwHI70yFQ6sqP9tpHxJt4O_5hHZwo5iRl0LuunUQZv4fC6t_V_Oay2LxuN4gYs9hLGEKxG0IoHjZ6-Y03L7BfpdBqoCQW1brM3_hH27uE3FhXzD9L54GBqhoYX4w_kKT",
            imageUrl = "https://lh3.googleusercontent.com/aida/AP1WRLuC9MHmzEmUKpqf02wb5eefGuFvFRRnHm-WnW_YEOF4_m8a8sGEDVlPZNteKVN9NT_66928HuJNq6_g2iTCPhc1icDn9WuL0GahOQ4WUB4ZnMhgunP4KaDdZO8dkzf_RRJPy9RwCK-0XjfSbRLRTOW0uFEk0ZXR9-LV_gVzuQUgIwuvl_BQVRvU-Tu62JLIAoTI-nCajA1fd6Nm0iWaGc5HkzkGDYjOMcu6fVpBG9cHbEn0lSZYHexLbDQd",
            hoursAlert = "NOW OPEN",
            isFeatured = true,
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "SIGNATURE BREWS",
                    items = listOf(
                        MenuItem("Twisted Pale Ale", "Crisply hopped original pale ale with hints of sweet citrus and dry ginger notes.", "$8"),
                        MenuItem("Ginger Session IPA", "Light, refreshing session IPA with organic locally-harvested ginger punch.", "$8"),
                        MenuItem("Foundry Stout", "Heavy-set nitro stout styled with rich espresso roast and raw dark chocolate finish.", "$9")
                    )
                ),
                MenuCategory(
                    categoryName = "FOUNDRY BITES",
                    items = listOf(
                        MenuItem("Twisted Pretzel", "Salty, wood-fired soft giant pretzel served with stout-infused cheddar dipping sauce.", "$6"),
                        MenuItem("Personal Pepperoni", "Stone-baked brick-oven sourdough flat pizza loaded with hot honey and crisp cupped pepperoni.", "$12")
                    )
                )
            )
        ),
        Vendor(
            id = "pho-mi-please",
            name = "PHO MI PLEASE",
            tagline = "Vietnamese comfort in every bowl.",
            description = "Authentic traditional noodle soup, banh mi, and spring rolls cooked to order. Slow-simmered 24-hour beef broth combined with handcrafted local herbs.",
            category = "Comfort",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLv0VkOEdShk-97MEJADWsS6idieaBDrfAO_MRA_uAJKseWwMYpH-GShdI0ZBPtXBBYAWAi3mTefwfiPaFH1t4-3ZOSAqC28F_cxTGPuIFLYPhaUhZBkHW2mtfgtOSQsojs5bHbJYg-athtIQP5S5nJM6oh9O7Gh1TMWx9J8VLq3p5ue83ULLzVjCqRMDHQxZmgd6-p3LWmLIbo7uzixbuxxLoaah4HFBeypKFDHCcH5lDgkOMswTwDlvwaC",
            imageUrl = "https://lh3.googleusercontent.com/aida/AP1WRLvLOkUgH7jzGanMRr1ghGjtein6Lx28uSX4spN0PAbnqhO6db6aL1eTEcSAsNsmfszSOG6hVFus-WARIPV1jtISUALwvp-sxULWE9lBfRHiexho4egqZzjF7T-rdYirQPM70G9YYZHymi6yttjCtA2nwCwe_3cIVRrd0-vnqtmGSIiCZ8Ded-MOyWpk3XnbqlBxdiu77kO9urpO4v5ATuRD_YOiBf516zKTDKpRaQGdJWqgfKdC-BlwI-8",
            hoursAlert = "NOW OPEN",
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "SIGNATURE PHO",
                    items = listOf(
                        MenuItem("Pho Tai Cow Beef", "Delicate rice noodles loaded with rare ribeye steak, shaved onions, and fresh cilantro.", "$14"),
                        MenuItem("Pho Ga Fresh Chicken", "Shredded organic chicken breast served in clear aromatic chicken bone broth.", "$14"),
                        MenuItem("Pho Chay Veggie Harvest", "Healthy combination of grilled tofu, broccoli, and mushrooms in spiced star-anise broth.", "$13")
                    )
                ),
                MenuCategory(
                    categoryName = "VIETNAMESE SANDWICHES",
                    items = listOf(
                        MenuItem("Classic Pork Banh Mi", "Toasted airy baguette smeared with savory paté, loaded with grilled pork and pickled radish.", "$10")
                    )
                )
            )
        ),
        Vendor(
            id = "char-lobster-rolls",
            name = "CHAR LOBSTER ROLLS",
            tagline = "Premium coastal catches. Fresh lobster, buttery rolls, and seaside flavors.",
            description = "Authentic New England coastal flavor served fresh in the heart of Ambler. Sourced daily from cold Atlantic waters.",
            category = "Seafood",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLv0VkOEdShk-97MEJADWsS6idieaBDrfAO_MRA_uAJKseWwMYpH-GShdI0ZBPtXBBYAWAi3mTefwfiPaFH1t4-3ZOSAqC28F_cxTGPuIFLYPhaUhZBkHW2mtfgtOSQsojs5bHbJYg-athtIQP5S5nJM6oh9O7Gh1TMWx9J8VLq3p5ue83ULLzVjCqRMDHQxZmgd6-p3LWmLIbo7uzixbuxxLoaah4HFBeypKFDHCcH5lDgkOMswTwDlvwaC",
            imageUrl = "https://lh3.googleusercontent.com/aida/AP1WRLvNgOIF-bbfaZ62vz9OxZLydA0o4pNlvm_yiOdjuRrg93VsUERbjOL1dk3QP_VCfhy-s7BcZ7wPlPUU_ql-pzdI7YV9ATHeXLFX5_aCSwiDVRj0cLVRRyGYK02Y-kMLwBhR-avEbTivJWYlsU3OEvcNzguojy2ZAMsE4P0JB8DGOw4pBYLZQ8JbV3TjHALs0v0wCgtehdZVjy1nW94DcSJ2qBCSEd4b0GF_ZhWHKW6Uk7QyFT3qDHkvAVow",
            hoursAlert = "NOW OPEN",
            location = "STALL #04 - RIDGE HALL\n15 S RIDGE AVE, AMBLER, PA",
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "SIGNATURE ROLLS",
                    items = listOf(
                        MenuItem("THE CLASSIC MAINE", "Chilled lobster meat, light mayo, lemon, and a dash of secret spices on a toasted brioche bun.", "$24"),
                        MenuItem("CONNECTICUT STYLE", "Warm, butter-poached lobster meat with fresh chives on a buttered-to-death roll.", "$26"),
                        MenuItem("TRUFFLE BUTTER SPECIAL", "Warm lobster tossed in black truffle infused butter, topped with micro-greens.", "$29")
                    )
                ),
                MenuCategory(
                    categoryName = "FOUNDRY SIDES",
                    items = listOf(
                        MenuItem("Cape Cod Chips", "Locally-crafted sea salt potato crisps.", "$3"),
                        MenuItem("Clam Chowder", "Thick New England-style soup packed with sea clams.", "$9"),
                        MenuItem("Sweet Potato Fries", "Waffle-cut sweet potato strips tossed in kosher salt.", "$6"),
                        MenuItem("Lobster Bisque", "Rich, creamy slow-simmered lobster puree.", "$10")
                    )
                )
            )
        ),
        Vendor(
            id = "luckys-roadside",
            name = "LUCKY'S ROADSIDE STAND",
            tagline = "Classic American road-trip favorites.",
            description = "Smash burgers and bites forged in iron for the modern nomad. Grilled on heavy cast iron to lock in absolute juiciness.",
            category = "Street Food",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLvqyHD1u9s7BXAinBM9gu9zADwAqyPWFwVBZ8PNwwbZOtKk1DIH9AGuHY64sgsGvPeHqZ1WbI4sB2roCA9WCgIlNcTFWTiuGM-iuoFfOA6JMPIJXmQIBbFj9eN5q9H87aANprt8UC9uRXYRYF6D2zbAVBkfVaVh9Dy96IakJA8PDfUdOUkinurgtOCmY23pVpYTr5U1tAJUrcWMgnPcWRty8VRFncDaUpzGkpOfFClfqB9GseG2--rCTWXV",
            imageUrl = "https://lh3.googleusercontent.com/aida/AP1WRLu2Wra8nZgaE87ROoHAa8TOYEVXotEFCKi9NsuHcCz8TtEsa0OU3-Zfgez5Gcfvia2hXpqix6WJedw3EWbbQz4TMWs7kXR2YngBEthaSbTRW2zocLlo5Rgzo8eTsAufJFHBCjcRuseBYGKW8mgzykWGJD4Z96P7RmSQ3vcM83rKQZQ6lC5SaiCZlHSB8qPyhf6Pxyfp-AuVKf7Se8uO9tQCrZSQBO8C4ZhsvUrYg65XdQ8x4CHSMgCg5_LY",
            hoursAlert = "NOW OPEN",
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "SMASH BURGERS",
                    items = listOf(
                        MenuItem("The Double Smash", "Two custom-blend smashed patties, double American cheese, caramelized onions, Lucky sauce.", "$12"),
                        MenuItem("BBQ Roadside Stack", "Double patty, crispy bacon, melted cheddar, smoky Kansas-style BBQ oil.", "$13"),
                        MenuItem("Veggie Forge Burger", "Flavorful flame-grilled house black bean patty with spicy jalapeño chipotle.", "$11")
                    )
                )
            )
        ),
        Vendor(
            id = "double-down",
            name = "DOUBLE DOWN KITCHEN",
            tagline = "High-stakes flavor.",
            description = "Bold, experimental dishes that go all-in on taste and texture. From high-stakes waffles to custom pork belly combinations.",
            category = "Comfort",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLupa9JFIpMMXA3OfUPQ54dlzgZsaszVT0jB5XvjZn3_rhSXj7ViVulGYJrF06fYxc3ugXwCgVYel9AxIze0I-CXR7IZ4vwyVbmWZfCrAFBRkJjIWeLIV6Mb8yxOKVGA2-Bm7O5WJak5cgTB6HRjb8dDZUjSSEZ1UH7Grqr1dH3OKTMVezWNnNnkdje4d7W7hd3JxICwK2GOJ6jQxcNnuogGrTXj2ahdgYyEwZHLrLHyAAv8Fw9GCVdLfNg",
            imageUrl = "https://lh3.googleusercontent.com/aida/AP1WRLuIxO7FGIFek2nZvkOpnbPe1dK8lOAoOkCJxKwqDVlACZxxe8-75BHCwDiT0dWdKznDid5oCAr8QzKYkB4fHHqhZRc-Qw3MWMHyDdBByBMCVFRlX87FNElg9uMALCrGTnZCGaXYJrIVseqWEsNoocyYiI30hFtFWsQ0s2vpYuRGfNRy2bkbkN-glMhxMSO7QE0I5wZayM7OgChLaXFHNd0xfrGkMqJDEFtUdjfNhu_QhDR2VUE_d2khsHo",
            hoursAlert = "NOW OPEN",
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "DOUBLE DOWN SPECIALS",
                    items = listOf(
                        MenuItem("Chicken & Sweet Waffle", "Giant dry-brined double fried chicken breast nested on salted butter malt waffles.", "$14"),
                        MenuItem("Pork Belly Fries", "Crinkle fries loaded with crispy caramelized pork belly bites and sour-cream chive reduction.", "$12")
                    )
                )
            )
        ),
        Vendor(
            id = "marys-chicken",
            name = "MARY'S CHICKEN STRIP CLUB",
            tagline = "The elite choice for crispy, hand-breaded chicken.",
            description = "Hand-breaded premium breast tenders, buttermilk-soaked and dry-brushed with our proprietary secret spice mixture.",
            category = "Comfort",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLvFAfrEMsAa38K66xZ9y5wpxfM5oaJKl4q8XkHojl_vaa0zSwkoGyLRM7ZdvKocR7A6H1piXbq65KtJltNi1oS6YlJP3iSTHYzvhYjh-8nXtgfkrMDQX30lkqjuC_KZK-R3pVcxrsukXiah7alOnSiSDENdXe5tK6vYgUvtngY-YII_8jD2x156OB-KMtKlUvRFEjfy1_jqYsqhkssoveJOQOZzniugYh_59jvFJGR62cSY81nVoZxdqe4",
            imageUrl = "https://lh3.googleusercontent.com/aida/AP1WRLvFAfrEMsAa38K66xZ9y5wpxfM5oaJKl4q8XkHojl_vaa0zSwkoGyLRM7ZdvKocR7A6H1piXbq65KtJltNi1oS6YlJP3iSTHYzvhYjh-8nXtgfkrMDQX30lkqjuC_KZK-R3pVcxrsukXiah7alOnSiSDENdXe5tK6vYgUvtngY-YII_8jD2x156OB-KMtKlUvRFEjfy1_jqYsqhkssoveJOQOZzniugYh_59jvFJGR62cSY81nVoZxdqe4",
            hoursAlert = "NOW OPEN",
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "SIGNATURE STRIPS",
                    items = listOf(
                        MenuItem("The Elite 5-Piece", "Five giant crunchy hand-dipped tenders served with classic Mary sauce and honey mustard.", "$12"),
                        MenuItem("Buttermilk Chicken Roll", "Fried thigh fillet, crisp lettuce, bread-and-butter pickles, sweet heat mayo on brioche.", "$11")
                    )
                )
            )
        ),
        Vendor(
            id = "top-dog",
            name = "TOP DOG COCKTAILS",
            tagline = "Craft spirits & social hub.",
            description = "Masterfully blended local spirits, barrel-aged custom beers, and custom cocktails served on a premium iron-pipe bar.",
            category = "Brews",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLuS0I-zDhgblyT-dA9LqOG9mcy35hhYmB3z1nMoVKWfWh1Si4oZE9EdzXzVGKDY0ft_quY8MxKVUQrdPAD25e0jmvFpZJDVRC9LDwKHu6RPJqGHFprZ3wxlbnScrCBbTiM2sPWW6bCeI__MFPhCyWy6xMH8ZhMRsEINttQJA0J5X3QRjPHFl1ZwM-N_4m0w5X7ruFdYBy0E8I_ifoc7Jb6LloZDfS03CMquf6Ef9xlmdbTXl7piz5W5lkQC",
            imageUrl = "https://lh3.googleusercontent.com/aida/AP1WRLuS0I-zDhgblyT-dA9LqOG9mcy35hhYmB3z1nMoVKWfWh1Si4oZE9EdzXzVGKDY0ft_quY8MxKVUQrdPAD25e0jmvFpZJDVRC9LDwKHu6RPJqGHFprZ3wxlbnScrCBbTiM2sPWW6bCeI__MFPhCyWy6xMH8ZhMRsEINttQJA0J5X3QRjPHFl1ZwM-N_4m0w5X7ruFdYBy0E8I_ifoc7Jb6LloZDfS03CMquf6Ef9xlmdbTXl7piz5W5lkQC",
            hoursAlert = "NOW OPEN",
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "TAP COCKTAILS",
                    items = listOf(
                        MenuItem("Steel-Worker Mule", "Blueberry-infused vodka, spicy double-fermented ginger craft beer, fresh dark lime oil.", "$12"),
                        MenuItem("Foundry Whiskey Sour", "Aged bourbon, fresh lemon foam, pure sugar syrup, served with charred orange wheel.", "$13")
                    )
                )
            )
        ),
        Vendor(
            id = "pa-general",
            name = "PA GENERAL & WANNA SPOON",
            tagline = "Local provisions & sweet rewards.",
            description = "The ultimate end to a Faction feast. Stocked with artisanal Pennsylvania crafts, sweet spreads, gourmet spoons, and local gifts.",
            category = "Comfort",
            logoUrl = "https://lh3.googleusercontent.com/aida/AP1WRLvFAfrEMsAa38K66xZ9y5wpxfM5oaJKl4q8XkHojl_vaa0zSwkoGyLRM7ZdvKocR7A6H1piXbq65KtJltNi1oS6YlJP3iSTHYzvhYjh-8nXtgfkrMDQX30lkqjuC_KZK-R3pVcxrsukXiah7alOnSiSDENdXe5tK6vYgUvtngY-YII_8jD2x156OB-KMtKlUvRFEjfy1_jqYsqhkssoveJOQOZzniugYh_59jvFJGR62cSY81nVoZxdqe4",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFM4ZAs2E1Pwg2gTA-YiTg6F9iHuExUe-kns8u0JsfopoJGvRbHozNkqN8Jzz4nO6f-vVwRkIJaojZZQHaTu84gC8tVHwWvacX0hWSgLd7-w-O7qVdll01aEqwMHn52jMNmjjp5ZnnIukUATqxpMQup6NVqFcALzYt241G6OM-z8ImiuyOHQGENeR-sqH-LMw1UY0-VmUO4d_tEEV1_rytCGYA0qXePsx8YY4GhC5qNoExtNoDs69kBtx6CEno8G9I7gSyA9p-Jbju",
            hoursAlert = "NOW OPEN",
            menuCategories = listOf(
                MenuCategory(
                    categoryName = "SWEET TREATS & PROVISIONS",
                    items = listOf(
                        MenuItem("Wanna Spoon Sundae", "Vanilla bean custard topped with crispy waffle crumble, dynamic dark chocolate drizzle.", "$8"),
                        MenuItem("Ambler Clover Honey Jar", "100% pure organic raw clover honey harvested in the neighboring hills of PA.", "$10")
                    )
                )
            )
        )
    )

    fun getVendorById(id: String): Vendor? {
        return allVendors.find { it.id == id }
    }
}
