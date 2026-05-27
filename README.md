# AE2 Wireless View Tabs

NeoForge 1.21.1 / AE2 addon project for wireless-terminal saved view tabs.

## Goal

Add a `View Tabs Card` upgrade for AE2 Wireless Terminals and Wireless Crafting Terminals.

The card stores a 3x9 ordered list of tab entries. Each entry becomes a clickable terminal tab.

Valid tab entries:

- AE2 View Cell
- Dynamic View Card

Dynamic View Cards are intended to use AE2 search syntax:

- `@modid` for mod search
- `#tag` for tag search
- `$tooltip` for tooltip search
- `*item_id` for item-id search
- normal text for name search
- `|` for OR searches

## Current status

This repo is being set up from the initial scaffold. The design is intentionally AE2-native: keep AE2's terminal, storage sync, extraction, and crafting behavior; add only a tab/bookmark layer on top.

## Build target

- Minecraft `1.21.1`
- NeoForge `21.1.230`
- Java `21`
- AE2 for Minecraft `1.21.1`

For local development, use your exact AE2 jar from the pack if Maven resolution is annoying.
